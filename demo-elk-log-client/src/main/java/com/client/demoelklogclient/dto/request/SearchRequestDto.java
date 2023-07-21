package com.client.demoelklogclient.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class SearchRequestDto {

  String searchTerm;
  List<String> fields;

}
