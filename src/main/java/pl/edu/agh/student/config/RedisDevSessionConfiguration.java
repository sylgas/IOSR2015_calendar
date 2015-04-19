package pl.edu.agh.student.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.Protocol;
import redis.embedded.RedisServer;

@Configuration
@Profile("dev")
public class RedisDevSessionConfiguration {

    @Bean(name = "redisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisServerBean redisServer() {
        return new RedisServerBean();
    }

    class RedisServerBean implements InitializingBean, DisposableBean {
        private RedisServer redisServer;

        public void afterPropertiesSet() throws Exception {
            redisServer = new RedisServer(Protocol.DEFAULT_PORT);
            redisServer.start();
        }

        public void destroy() throws Exception {
            if (redisServer != null) {
                redisServer.stop();
            }
        }
    }
}