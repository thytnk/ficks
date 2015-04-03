package com.fujielectric.ficks.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Hashtable;
import java.util.Map;

//@Configuration
public class WebContext extends DelegatingWebMvcConfiguration
{

//    @Bean
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping()
    {
        RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
        handlerMapping.setRemoveSemicolonContent(false);
//        handlerMapping.setUseTrailingSlashMatch(false);
        return handlerMapping;
    }

//    @Bean
    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter()
    {
        RequestMappingHandlerAdapter handlerApapter = super.requestMappingHandlerAdapter();
        handlerApapter.setIgnoreDefaultModelOnRedirect(true);
        return handlerApapter;
    }
}