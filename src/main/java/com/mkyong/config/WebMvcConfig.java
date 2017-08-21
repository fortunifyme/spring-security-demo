package com.mkyong.config;

import com.mkyong.dao.AccountDao;
import com.mkyong.service.AccountService;
import com.mkyong.web.controller.MainController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.sql.DataSource;

/**
 * inspired by : https://www.youtube.com/watch?v=5BY9YxdMg8I
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackageClasses = {AccountDao.class, AccountService.class, MainController.class})
@Import({SecurityConfig.class})
public class WebMvcConfig extends WebMvcConfigurerAdapter {
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
  }

  @Bean
  public MultipartResolver multipartResolver() {
    return new StandardServletMultipartResolver();
  }

  @Bean
  public DataSource dataSource() {
    final JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
    dataSourceLookup.setResourceRef(true);
    return dataSourceLookup.getDataSource("jdbc/ya_web_app_db");
  }

  @Bean
  public UrlBasedViewResolver urlBasedViewResolver() {
    UrlBasedViewResolver resolver = new UrlBasedViewResolver();
    resolver.setPrefix("/WEB-INF/pages/");
    resolver.setSuffix(".jsp");
    resolver.setViewClass(JstlView.class);
    return resolver;
  }
}