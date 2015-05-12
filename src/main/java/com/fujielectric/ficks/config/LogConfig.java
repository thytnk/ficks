package com.fujielectric.ficks.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.access.tomcat.LogbackValve;

import java.net.URL;

@Configuration
public class LogConfig {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer factory) {

                if(factory instanceof TomcatEmbeddedServletContainerFactory){
                    TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;

                    URL url = this.getClass().getResource("/logback-access.xml");
                    LogbackValve  logbackValve = new LogbackValve();
                    logbackValve.setFilename(url.getFile());
                    containerFactory.addContextValves(logbackValve);
                }
            }
        };
    }
}