package com.fujielectric.ficks.mvc;
import com.fujielectric.ficks.config.GuiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @Autowired
    GuiProperties guiProperties;

    @ModelAttribute
    GuiProperties guiProperties() {
        return guiProperties;
    }

    @RequestMapping("loginForm")
    String loginForm() {
        return "loginForm";
    }
}
