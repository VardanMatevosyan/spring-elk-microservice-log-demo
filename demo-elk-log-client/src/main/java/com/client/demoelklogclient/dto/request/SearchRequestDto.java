package com.client.demoelklogclient.dto.request;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class SearchRequestDto extends PageRequestDto {

  String searchTerm;
  List<String> fields;
  SortingDto sortingDto;
  FilteringDto filteringDto;

}
