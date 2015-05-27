package com.fujielectric.ficks.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ch.qos.logback.access.tomcat.LogbackValve;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.net.URL;
import java.util.Optional;

@Configuration
public class LogConfig {

    static final String LOGBACK_ACCESS = "logback.access";

    @Resource
    private Environment environment;

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer factory) {

                if(factory instanceof TomcatEmbeddedServletContainerFactory){
                    TomcatEmbeddedServletContainerFactory containerFactory = (TomcatEmbeddedServletContainerFactory) factory;
                    String logbackAccessConfig = environment.getRequiredProperty(LOGBACK_ACCESS);

                    LogbackValve  logbackValve = new LogbackValve();
                    if (logbackAccessConfig == null) {
                        URL url = this.getClass().getResource("/logback-access.xml");
                        logbackValve.setFilename(url.getFile());
                    } else {
                        logbackValve.setFilename(logbackAccessConfig);
                    }

                    containerFactory.addContextValves(logbackValve);
                }
            }
        };
    }
}