package com.upgle.api.domain.cache;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CacheService {

  private final StringRedisTemplate stringRedisTemplate;


  public boolean setCacheString(String key, String value, int ttl) {
    ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();

    valueOperations.set(key, value);
    stringRedisTemplate.expire(key, ttl, TimeUnit.SECONDS);

    return true;
  }

  public String getCacheString(String key) {
    ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
    return valueOperations.get(key);
  }


}
