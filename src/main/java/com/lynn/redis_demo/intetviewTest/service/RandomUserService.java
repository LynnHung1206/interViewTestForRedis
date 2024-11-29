package com.lynn.redis_demo.intetviewTest.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lynn.redis_demo.intetviewTest.service.dto.RandomUserResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Lynn on 2024/11/29
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RandomUserService {

  private static final String API_URL = "https://randomuser.me/api/?results=100";
  private static final String CACHE_KEY = "randomUsers";

  private final RedisTemplate<String, Object> redisTemplate;

  public void fetchAndCacheUsers() {
    RestTemplate restTemplate = new RestTemplate();
    RandomUserResponse response = restTemplate.getForObject(API_URL, RandomUserResponse.class);
    if (response != null && response.getResults() != null) {
      redisTemplate.opsForValue().set(CACHE_KEY, response.getResults(),10, TimeUnit.MINUTES);
    }
  }

  @SneakyThrows
  public RandomUserResponse getCachedUsers() {
    Object object = redisTemplate.opsForValue().get(CACHE_KEY);
    ObjectMapper objectMapper = new ObjectMapper();
    List<Map<String, Object>> resultList = objectMapper.convertValue(object, new TypeReference<List<Map<String, Object>>>() {});

    RandomUserResponse randomUserResponse = new RandomUserResponse();
    randomUserResponse.setResults(
        resultList.stream()
            .map(result -> objectMapper.convertValue(result, RandomUserResponse.Result.class))
            .toList()
    );
    return randomUserResponse;

  }
}

