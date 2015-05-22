package com.fujielectric.ficks.solr;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrOperations;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import javax.annotation.Resource;

@Configuration
@EnableSolrRepositories(basePackages={"com.fujielectric.ficks.solr"}, multicoreSupport=true)
public class SolrContext {

    static final String SOLR_HOST = "solr.host";

    @Resource
    private Environment environment;

    @Bean
    public SolrServer solrServer() {
        String solrHost = environment.getRequiredProperty(SOLR_HOST);
        return new HttpSolrServer(solrHost);
    }

    @Bean
    public SolrOperations solrTemplate() {
        return new SolrTemplate(solrServer());
    }
}
