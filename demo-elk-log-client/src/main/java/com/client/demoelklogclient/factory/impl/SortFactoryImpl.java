package com.client.demoelklogclient.factory.impl;



import static org.apache.logging.log4j.util.Strings.isNotBlank;

import com.client.demoelklogclient.dto.request.SortingDto;
import com.client.demoelklogclient.factory.SortFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SortFactoryImpl implements SortFactory {

  @Override
  public Sort build(SortingDto sortingDto) {
    return Sort.by(getDirection(sortingDto.getOrder()), sortingDto.getSortBy());
  }

  private Direction getDirection(String order) {
    return isNotBlank(order) && Direction.DESC.name().equalsIgnoreCase(order)
        ? Direction.DESC
        : Direction.ASC;
  }
}
