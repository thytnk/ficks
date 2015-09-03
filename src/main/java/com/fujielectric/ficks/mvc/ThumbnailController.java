package com.fujielectric.ficks.mvc;

import com.fujielectric.ficks.domain.ThumbnailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
@RequestMapping("/thumbnail")
public class ThumbnailController {
    private Logger log = LoggerFactory.getLogger(ThumbnailController.class);

    @Autowired
    ThumbnailService thumbnailService;

    @RequestMapping(value="{code}.jpg", produces="image/jpeg")
    public void thumbnail(HttpServletResponse res, @PathVariable("code")String code) throws IOException {
        log.info("thumbnail: {}", code);
        Path path = thumbnailService.getThumbnailOf(code);

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

}
