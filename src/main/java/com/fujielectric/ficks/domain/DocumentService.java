package com.fujielectric.ficks.domain;

import com.fujielectric.ficks.jpa.DocumentRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class DocumentService {
    private Logger log = LoggerFactory.getLogger(DocumentService.class);

    private static final String DOCUMENT_ROOT = "ficks.document.root";

    @Resource
    Environment environment;

    @Autowired
    DocumentRepository repository;

    @Transactional
    public void saveDataAndFile(Document document, String fileName, byte[] fileData) {
        log.info("go add");

        saveData(document, fileName);
        saveFile(document, fileData);

        log.info("end add");
    }

    private void saveData(Document document, String fileName) {
        document.code = documentCode(document);
        document.fileName = fileName;
        document.registerDate = new Date();
        repository.save(document);
    }

    /**
     * Zyy-00009-09 形式で文書管理番号を採番する
     * 種類別+年2桁-連番5桁-枝番2桁
     */
    private String documentCode(Document document) {
        return new StringBuilder()
                .append(document.category)
                .append(LocalDate.now().getYear() - 2000)
                .append('-')
                .append(LocalDateTime.now().getMinute())
                .append(LocalDateTime.now().getSecond())
                .append('-')
                .append("01")
                .toString();
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
        if (document.code == null || document.fileName == null)
            throw new IllegalStateException();

        String rootDirectory = environment.getRequiredProperty(DOCUMENT_ROOT);
        File parent = Paths.get(rootDirectory, document.code).toFile();
        parent.mkdir();
        return Paths.get(rootDirectory, document.code, document.fileName).toFile();
    }
}
