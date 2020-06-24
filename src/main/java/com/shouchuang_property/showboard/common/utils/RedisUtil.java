package com.shouchuang_property.showboard.common.utils;

import java.io.Serializable;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.stereotype.Component;

import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisTransactionalCommands;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;
        
    
    public void test( ) {
//    	redisReactiveCommands.multi().
//    	
//    	.doOnSuccess(r -> {
//    		redisReactiveCommands.set("aaa", "value").doOnNext(System.out::print);
//    		redisReactiveCommands.set("bbb", "value1").doOnNext(System.out::print);
//    	}).doOnError(r -> {
    		
//    	});
    }
}
