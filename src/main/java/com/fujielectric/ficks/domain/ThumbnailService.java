package com.fujielectric.ficks.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ThumbnailService {
    private Logger log = LoggerFactory.getLogger(ThumbnailService.class);

    private static final String THUMBNAIL_ROOT = "ficks.thumbnail.root";
    private static final String THUMBNAIL_DEFAULT = "ficks.thumbnail.default";

    @Resource
    Environment environment;

    public void saveThumbnail(Document document, byte[] fileData) {
        log.debug("save thumbnail");
        Path path = thumbnailPath(document.getCode());

        try (OutputStream stream = Files.newOutputStream(path)) {
            stream.write(fileData);
            log.info("save thumbnail: {}", path.toString());
        } catch (IOException e) {
            log.error("exception on saving thumbnail:", e);
        }
    }

    public Path getThumbnailOf(String code) {
        Path path = thumbnailPath(code);
        if(Files.exists(path)) {
            return path;
        } else {
            String rootDirectory = environment.getRequiredProperty(THUMBNAIL_ROOT);
            String defaultFile = environment.getRequiredProperty(THUMBNAIL_DEFAULT);
            return Paths.get(rootDirectory, defaultFile);
        }
    }

    private Path thumbnailPath(String code) {
        String rootDirectory = environment.getRequiredProperty(THUMBNAIL_ROOT);
        return Paths.get(rootDirectory, code + ".jpg");
    }
}
