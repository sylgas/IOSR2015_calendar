package pl.edu.agh.student.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.util.Arrays;

@Configuration
@ComponentScan({"pl.edu.agh.student.config"})
@EnableMongoRepositories(basePackages = "pl.edu.agh.student.db.repository")
@EnableSpringDataWebSupport
public class MongoConfig extends AbstractMongoConfiguration {

    @Configuration
    @Profile("default")
    @PropertySource("classpath:mongo.properties")
    static class CloudConfig {}

    @Configuration
    @Profile({"dev", "test"})
    @PropertySource({"classpath:mongo.dev.properties"})
    static class DevConfig {}

    @Value("${mongodb.name}")
    private String databaseName;

    @Value("${mongodb.host}")
    private String databaseHost;

    @Value("${mongodb.port}")
    private int databasePort;

    @Value("${mongodb.username}")
    private String username;

    @Value("${mongodb.password}")
    private String password;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoCredential credential = MongoCredential.createCredential(username, getDatabaseName(), password.toCharArray());
        return new MongoClient(new ServerAddress(databaseHost, databasePort), Arrays.asList(credential));
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}