package com.devin.order.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author Devin Zhang
 * @className RedisClient
 * @description TODO
 * @date 2020/4/24 17:51
 */

@Slf4j
@Component
public class RedisClient {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init() {
        redisTemplate.setKeySerializer(new GenericToStringSerializer<>(String.class));
    }


    /**
     * redis存值
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * hash存
     *
     * @param key   键
     * @param hash  hash
     * @param value 值
     */
    public void set(String key, String hash, String value) {
        redisTemplate.opsForHash().put(key, hash, value);
    }


    /**
     * redis获取值
     *
     * @param key 键
     * @return 返回值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * hash取值
     *
     * @param key  键
     * @param hash hash
     * @return 返回redis存储的值
     */
    public String get(String key, String hash) {
        return (String) redisTemplate.opsForHash().get(key, hash);
    }


    /**
     * 获取redis的锁
     *
     * @param key   键
     * @param value 值为当前毫秒数+过期时间毫秒数
     * @return 返回true/false
     */
    public boolean lock(String key, String value) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            //加锁成功就返回true
            return true;
        }
        //不加下面这个可能出现死锁情况
        //value为当前时间+超时时间
        //获取上一个锁的时间,并判断是否小于当前时间,小于就下一步判断,就返回true加锁成功
        //currentValue=A 这两个线程的value都是B 其中一个线程拿到锁
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        //如果锁过期
        if (!StringUtils.isEmpty(currentValue)
                && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            //存储时间要小于当前时间
            //出现死锁的另一种情况,当多个线程进来后都没有返回true,接着往下执行,执行代码有先后,而if判断里只有一个线程才能满足条件
            //oldValue=currentValue
            //多个线程进来后只有其中一个线程能拿到锁(即oldValue=currentValue),其他的返回false
            //获取上一个锁的时间
            String oldValue = (String) redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                //上一个时间不为空,并且等于当前时间
                return true;
            }

        }
        return false;
    }


    /**
     * redis释放锁
     *
     * @param key   键
     * @param value 值
     */
    public void unlock(String key, String value) {
        //执行删除可能出现异常需要捕获
        try {
            String currentValue = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                //如果不为空,就删除锁
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("[redis分布式锁] 解锁", e);
        }
    }

}
