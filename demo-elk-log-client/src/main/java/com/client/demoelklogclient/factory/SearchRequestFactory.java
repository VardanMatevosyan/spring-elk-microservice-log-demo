package com.client.demoelklogclient.factory;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.client.demoelklogclient.dto.request.SearchRequestDto;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;

public interface SearchRequestFactory {

  NativeQuery build(SearchRequestDto searchRequest);

}
