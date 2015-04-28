package pl.edu.agh.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Profile("default")
public class RedisCloudConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        try {
            URI redisURI = new URI(System.getenv("REDISTOGO_URL"));
            JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(new JedisPoolConfig());
            redisConnectionFactory.setHostName(redisURI.getHost());
            redisConnectionFactory.setPort(redisURI.getPort());
            redisConnectionFactory.setTimeout(Protocol.DEFAULT_TIMEOUT);
            redisConnectionFactory.setPassword(redisURI.getUserInfo().split(":", 2)[1]);
            redisConnectionFactory.setUsePool(true);

            return redisConnectionFactory;
        } catch (URISyntaxException e) {
            throw new RuntimeException("Redis couldn't be configured from URL in REDISTOGO_URL env var:"+
                    System.getenv("REDISTOGO_URL"));
        }
    }
}
