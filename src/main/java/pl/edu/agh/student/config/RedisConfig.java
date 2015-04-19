package pl.edu.agh.student.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
@Import({RedisCloudSessionConfiguration.class, RedisDevSessionConfiguration.class})
public class RedisConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
}
