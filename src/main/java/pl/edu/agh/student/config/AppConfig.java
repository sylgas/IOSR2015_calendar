package pl.edu.agh.student.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({"pl.edu.agh.student.config"})
@Import(MongoConfig.class)
public class AppConfig {

}
