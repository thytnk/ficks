package com.fujielectric.ficks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Configuration
public class GuiConfig {

    static final String UI_FOOTER = "ficks.ui.footer";

    @Resource
    private Environment environment;

    @Bean
    public GuiProperties guiProperties() {
        String footerString = environment.getRequiredProperty(UI_FOOTER);
        GuiProperties gui = new GuiProperties();
        gui.setFooter(footerString);
        return gui;
    }
}
