package pl.edu.agh.student.config;


import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan({"pl.edu.agh.student.config"})
public class AppConfig {

}
