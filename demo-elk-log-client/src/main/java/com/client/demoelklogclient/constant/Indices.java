package com.client.demoelklogclient.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Indices {
  USER("user_v2");

  private final String indexName;

}
