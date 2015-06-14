package com.fujielectric.ficks.domain;

import static org.springframework.data.domain.Sort.Direction.*;

import com.fujielectric.ficks.jpa.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.query.PartialUpdate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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

    public List<Document> listSortedByDate() {
        return repository.findAll(new Sort(
                new Order(DESC, "updateDate"),
                new Order(DESC, "code")
        ));
    }

    @Transactional
    public void saveDataAndFile(Document document, byte[] fileData) {
        saveData(document);
        saveFile(document, fileData);
    }

    @Transactional
    public void saveData(Document document) {
        log.info("save data");
        repository.saveAndFlush(document); // 連番生成のためflushの必要あり
    }

    private void saveFile(Document document, byte[] fileData) {
        log.info("save file");
        Path path = prepareDirectory(document);

        try (OutputStream stream = Files.newOutputStream(path)) {
            stream.write(fileData);
        } catch (IOException e) {
            log.error("exception on saving file:", e);
        }
    }

    /** ファイルの保存先ディレクトリ */
    public Path prepareDirectory(Document document) {
        if (document.documentCode() == null || document.getFileName() == null)
            throw new IllegalStateException();

        String rootDirectory = environment.getRequiredProperty(DOCUMENT_ROOT);
        Path path = Paths.get(rootDirectory, document.getCode());

        try {
            if (Files.exists(path)) {
                // for update
                Files.list(path).forEach(child -> {
                    try {
                        Files.delete(child);
                    } catch (IOException e) {
                        log.error("File delete failed: {}", child);
                    }
                });
            } else {
                // for create
                Files.createDirectory(path);
            }
        } catch (IOException e) {
            log.error("Prepare Directory failed.");
        }
        return Paths.get(rootDirectory, document.getCode(), document.getFileName());
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
        update.setValueOfField("doc_disabled", document.isDisabled());

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
