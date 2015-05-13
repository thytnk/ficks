package com.fujielectric.ficks.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/form")
public class DocumentAddController extends WebMvcConfigurerAdapter {
    private Logger log = LoggerFactory.getLogger(DocumentAddController.class);

    @Autowired
    private GuiUtils gui;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method=GET)
    public String addForm(DocumentAddCommand documentAddCommand, Model model) {
        gui.addDropDowns(model);
        return "input";
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method=POST)
    public String add(@Valid DocumentAddCommand documentAddCommand, BindingResult result, Model model) {
        log.info("add:");
        gui.addDropDowns(model);

        log.info("category = {}", documentAddCommand.category);
        log.info("authorName = {}", documentAddCommand.authorName);
        if (result.hasErrors()) {
            log.info("has errors:");
            for (Object error: result.getAllErrors()) {
                log.info(error.toString());
            }
            return "input";
        }

        log.info("success:");
        return "input";
    }
}
