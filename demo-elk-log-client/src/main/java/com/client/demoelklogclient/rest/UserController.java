package com.client.demoelklogclient.rest;

import com.client.demoelklogclient.entity.User;
import com.client.demoelklogclient.service.UserLoggingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

  private final UserLoggingService userLoggingService;

  @PostMapping("/user")
  public void createUser(@RequestBody User user, HttpServletRequest request) {
    log.info("TRACE_ID: %s. User: %s.".formatted(request.getHeader("TRACE_ID"), user));
    userLoggingService.saveUser(user);
    log.info("TRACE_ID: %s. User created: %s".formatted(request.getHeader("TRACE_ID"), user));
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<User> getUser(@PathVariable Long id, HttpServletRequest request) {
    log.info("TRACE_ID: %s. Getting user by id: %s.".formatted(request.getHeader("TRACE_ID"), id));
    User user = userLoggingService.getUser(id);
    return ResponseEntity.ok(user);
  }

}
