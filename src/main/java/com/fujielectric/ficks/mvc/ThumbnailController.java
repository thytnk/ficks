package com.fujielectric.ficks.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/thumbnail")
public class ThumbnailController {
    private Logger log = LoggerFactory.getLogger(ThumbnailController.class);

    private static final String THUMBNAIL_ROOT = "ficks.thumbnail.root";
    private static final String THUMBNAIL_DEFAULT = "ficks.thumbnail.default";

    @Resource
    Environment environment;

    @RequestMapping(value="{code}.jpg", produces="image/jpeg")
    public void thumbnail(HttpServletResponse res, @PathVariable("code")String code) throws IOException {
        log.info("thumbnail: {}", code);
        Path path = getThumbnailOf(code);

        res.reset();
        res.setHeader("Content-Transfer-Encoding", "binary");

        OutputStream os = res.getOutputStream();
        InputStream in = Files.newInputStream(path);

        byte[] b = new byte[1024];
        int len;
        while((len = in.read(b)) != -1) {
            os.write(b, 0, len);
        }
        in.close();
        os.close();
    }

    public Path getThumbnailOf(String code) {
        String rootDirectory = environment.getRequiredProperty(THUMBNAIL_ROOT);

        Path path = Paths.get(rootDirectory, code + ".jpg");

        if(Files.exists(path)) {
            return Paths.get(rootDirectory, code + ".jpg");
        } else {
            String defaultFile = environment.getRequiredProperty(THUMBNAIL_DEFAULT);
            return Paths.get(rootDirectory, defaultFile);
        }
    }
}
