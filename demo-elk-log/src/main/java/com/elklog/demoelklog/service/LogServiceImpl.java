package com.elklog.demoelklog.service;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogServiceImpl implements LogService {

  private final RestTemplate restTemplate;

  @Override
  public String sendDemoLog() {
    String demoLogContent = "Demo log request to log client";
    send(demoLogContent);
    return demoLogContent;
  }

  private void send(String demoLogContent) {
    UUID traceId = UUID.randomUUID();
    log.info("sending request from demo-elk app with trace id " + traceId);


    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(List.of(MediaType.TEXT_PLAIN));
    headers.setContentType(MediaType.TEXT_PLAIN);
    headers.add("TRACE_ID", traceId.toString());

    HttpEntity<String> requestEntity =
        new HttpEntity<>(demoLogContent, headers);

    restTemplate.exchange(
        "http://localhost:8078/log",
        HttpMethod.POST,
        requestEntity,
        String.class);
  }
}
