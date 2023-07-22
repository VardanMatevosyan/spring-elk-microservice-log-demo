package com.client.demoelklogclient.factory;

import com.client.demoelklogclient.dto.request.SortingDto;
import org.springframework.data.domain.Sort;

public interface SortFactory {

  Sort build(SortingDto sortingDto);

}
