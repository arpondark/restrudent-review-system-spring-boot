package com.shazan.restrudent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.shazan.restrudent.repo")
public class ElasticsearchConfig {
}
