package com.elklog.demoelklog.rest;

import com.elklog.demoelklog.service.LogService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LogController {

  private final LogService logService;

  @GetMapping("/")
  public String rootPage() {
    log.info("You are requesting root page. Time: " + LocalDateTime.now());
    return "Return from root page endpoint";
  }

  @GetMapping("/log")
  public String logPage() {
    log.info("You are requesting log page endpoint. Time: " + LocalDateTime.now());
    return "Return from log page endpoint";
  }

  @GetMapping("/warning")
  public String warning() {
    log.warn("You are requesting warning endpoint. Time: " + LocalDateTime.now());
    return "Return from warning endpoint";
  }

  @GetMapping("/er")
  public String error() {
    log.error("You are requesting error endpoint. Time: " + LocalDateTime.now());
    return "Return from error endpoint";
  }

  @GetMapping("/client/log")
  public String clientLog() {
    log.error("You are requesting client log content endpoint. Time: " + LocalDateTime.now());
    String logContent = logService.sendDemoLog();
    return "Return from error endpoint. Log content: %s".formatted(logContent);
  }
}
