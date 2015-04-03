package com.fujielectric.ficks.mvc;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value="/download", method=GET)
public class DownloadController {

    @RequestMapping(produces="application/force-download")
    public void download(HttpServletResponse res, @RequestParam("file") String fileName) throws Exception {
        File file = new File(fileName);

//        res.setContentType("text/csv;charset=MS932");
        res.setHeader("Content-Disposition", "attachment; filename=" + file.getName());

        OutputStream os = res.getOutputStream();

        InputStream in = new FileInputStream(file);
        //InputStream in = new FileInputStream("C:\\LocalPrograms\\solr-5.0.0\\docs_test\\FGeeMS マニュアル_0.基本操作編_20140424.docx");//dir + targetFiles[0]);

        byte[] b = new byte[1024];
        int len;
        while((len = in.read(b)) != -1) {
            os.write(b, 0, len);
        }
        in.close();
        os.close();
    }

    //@RequestMapping(value = "/file2", method=GET)
  //  @RequestMapping(method=GET, produces="application/force-download")
    public Resource file2() {
        return new FileSystemResource("C:\\LocalPrograms\\solr-5.0.0\\docs_test\\DB テーブル設計_20140226.xls");
    }

}
