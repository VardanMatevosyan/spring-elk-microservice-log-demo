package com.client.demoelklogclient.rest;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LogController {

  @PostMapping("/log")
  public void log(@RequestBody String logContent, HttpServletRequest request) {
    int logNumber = new Random().nextInt(5);
    if (logNumber < 3) {
      log.info("TRACE_ID: %s. Successfully get log content: %s."
          .formatted(request.getHeader("TRACE_ID"), logContent));
    } else {
      log.error("TRACE_ID: %s. Error while processing log content: %s."
          .formatted(request.getHeader("TRACE_ID"), logContent));
    }
  }
}
