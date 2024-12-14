package edu.gatech.cs6310;

import java.util.stream.Stream;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.support.DatabaseStartupValidator;

@SpringBootApplication
public class DeliveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryServiceApplication.class, args);
	}
	
	@Bean
    public DatabaseStartupValidator databaseStartupValidator(DataSource dataSource) {
		DatabaseStartupValidator dsv = new DatabaseStartupValidator();
        dsv.setDataSource(dataSource);
        dsv.setTimeout(180);
        dsv.setInterval(10);
        dsv.setValidationQuery(DatabaseDriver.MYSQL.getValidationQuery());
        return dsv;
    }
	
	@Bean
	public static BeanFactoryPostProcessor dependsOnPostProcessor() {
	    return bf -> {

	        String[] jpa = bf.getBeanNamesForType(EntityManagerFactory.class);
	        Stream.of(jpa)
	                .map(bf::getBeanDefinition)
	                .forEach(it -> it.setDependsOn("databaseStartupValidator"));
	    };
	}
}