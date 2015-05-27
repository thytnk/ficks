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
        document.setFileName(fileName);
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
        if (document.documentCode() == null || document.getFileName() == null)
            throw new IllegalStateException();

        String rootDirectory = environment.getRequiredProperty(DOCUMENT_ROOT);
        File parent = Paths.get(rootDirectory, document.getCode()).toFile();
        parent.mkdir();
        return Paths.get(rootDirectory, document.getCode(), document.getFileName()).toFile();
    }

    /** 文書のインデックスを更新 */
    public void updateIndex(Document document) {
        PartialUpdate update = new PartialUpdate("id", document.getId());
        update.setValueOfField("doc_code", document.getCode());
        update.setValueOfField("doc_category", document.getCategory());
        update.setValueOfField("doc_area", document.getArea());
        update.setValueOfField("doc_purpose", document.getPurpose());
        update.setValueOfField("doc_result", document.getResult());
        update.setValueOfField("doc_reason", document.getReason());
        update.setValueOfField("doc_file_name", document.getFileName());
        update.setValueOfField("doc_dept_name", document.getDeptName());
        update.setValueOfField("doc_emp_number", document.getEmpNumber());
        update.setValueOfField("doc_publish_date", document.getPublishDate());
        update.setValueOfField("doc_register_date", document.getRegisterDate());
        update.setValueOfField("doc_author_name", document.getAuthorName());
        update.setValueOfField("doc_description", document.getDescription());
        update.setValueOfField("doc_customer_name", document.getCustomerName());

        solrTemplate.saveBean(update);
        solrTemplate.commit();
    }

    public Path getPathOf(String code) {
        Optional<Document> opt = repository.findByCode(code);
        if (opt.isPresent()) {
            String rootDirectory = environment.getRequiredProperty(DOCUMENT_ROOT);
            return Paths.get(rootDirectory, opt.get().getCode(), opt.get().getFileName());
        }
        throw new NullPointerException();
    }
}
