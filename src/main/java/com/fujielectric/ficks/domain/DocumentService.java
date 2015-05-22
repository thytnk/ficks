package com.fujielectric.ficks.domain;

import com.fujielectric.ficks.jpa.DocumentRepository;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.PartialUpdate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class DocumentService {
    private Logger log = LoggerFactory.getLogger(DocumentService.class);

    private static final String DOCUMENT_ROOT = "ficks.document.root";

    @Resource
    Environment environment;

    @Autowired
    DocumentRepository repository;


    @Autowired
    private SolrOperations solrTemplate;

    @Transactional
    public void saveDataAndFile(Document document, String fileName, byte[] fileData) {
        log.info("go add");

        saveData(document, fileName);
        saveFile(document, fileData);

        log.info("end add");
    }

    private void saveData(Document document, String fileName) {
        document.fileName = fileName;
        repository.save(document);
        repository.flush(); // 連番生成のためflushの必要あり
    }

    private void saveFile(Document document, byte[] fileData) {
        File file = prepareFile(document);

        try (BufferedOutputStream stream =
                     new BufferedOutputStream(new FileOutputStream(file))){
            stream.write(fileData);
        } catch (IOException e) {
            log.error("exception on saving file:", e);
        }
    }

    /** ファイルの保存先ディレクトリ */
    public File prepareFile(Document document) {
        if (document.documentCode() == null || document.fileName == null)
            throw new IllegalStateException();

        String rootDirectory = environment.getRequiredProperty(DOCUMENT_ROOT);
        File parent = Paths.get(rootDirectory, document.code).toFile();
        parent.mkdir();
        return Paths.get(rootDirectory, document.code, document.fileName).toFile();
    }

    /** 文書のインデックスを更新 */
    public void updateIndex(Document document) {
        PartialUpdate update = new PartialUpdate("id", document.id);
        update.setValueOfField("doc_code", document.code);
        update.setValueOfField("doc_category", document.category);
        update.setValueOfField("doc_area", document.area);
        update.setValueOfField("doc_purpose", document.purpose);
        update.setValueOfField("doc_result", document.result);
        update.setValueOfField("doc_reason", document.reason);
        update.setValueOfField("doc_file_name", document.fileName);
        update.setValueOfField("doc_dept_name", document.deptName);
        update.setValueOfField("doc_emp_number", document.empNumber);
        update.setValueOfField("doc_publish_date", document.publishDate);
        update.setValueOfField("doc_register_date", document.registerDate);
        update.setValueOfField("doc_author_name", document.authorName);
        update.setValueOfField("doc_description", document.description);
        update.setValueOfField("doc_customer_name", document.customerName);

        solrTemplate.saveBean(update);
        solrTemplate.commit();
    }

    public Path getPathOf(String code) {
        Optional<Document> opt = repository.findByCode(code);
        if (opt.isPresent()) {
            String rootDirectory = environment.getRequiredProperty(DOCUMENT_ROOT);
            return Paths.get(rootDirectory, opt.get().code, opt.get().fileName);
        }
        throw new NullPointerException();
    }
}
