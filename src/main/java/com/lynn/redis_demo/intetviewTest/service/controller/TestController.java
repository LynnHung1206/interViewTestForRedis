package com.lynn.redis_demo.intetviewTest.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lynn.redis_demo.intetviewTest.service.dto.RandomUserResponse;
import com.lynn.redis_demo.intetviewTest.service.RandomUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @Author: Lynn on 2024/11/29
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class TestController {

  private final RandomUserService dataService;

  @RequestMapping(value = "/users", method = {GET})
  public Object getUsers() {
    RandomUserResponse cachedUsers = dataService.getCachedUsers();
    if (CollectionUtils.isEmpty(cachedUsers.getResults())) {
      dataService.fetchAndCacheUsers();
      cachedUsers = dataService.getCachedUsers();
    }
    cachedUsers.getResults().forEach(result -> {
      try {
        log.info("result={}", new ObjectMapper().writeValueAsString(result));;
      } catch (Exception e) {
        log.info("", e);
      }
    });
    return ResponseEntity.ok().build();
  }
}
