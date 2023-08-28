package com.example.application.chat.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.ConfigureRedisAction;


@Configuration
public class RedisConfig {

    public static final int EXPIRATION_DAYS = 1;

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }


    /*
      Redis 버전이 오래되었을 때 발생하거나 외부 Redis를 연결할 때 발생하는 오류인데
      기본적으로 @EnalbeRedisHttpSession을 사용하는 경우 SessionMessageListner와 필요한 Redis Keyspace 이벤트 활성화가 자동으로 수행된다.
      그러나 보안을 가진 Redis환경에서는 config명령이 자동으로 비활성화가 되고, 이 의미는 Spring Session이 Redis Keyspace이벤트를 구성할 수 없음을 의미한다.
      그래서 자동 Configuration을 비활성화하려면 ConfigureRedisAction.NO_OP을 Bean으로 추가
     */

    @Bean
    public ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

}