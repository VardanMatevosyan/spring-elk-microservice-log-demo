package com.client.demoelklogclient.service.impl;

import com.client.demoelklogclient.dto.request.SearchRequestDto;
import com.client.demoelklogclient.entity.User;
import com.client.demoelklogclient.factory.SearchRequestFactory;
import com.client.demoelklogclient.service.UserSearchService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSearchServiceImpl implements UserSearchService {

  private final ElasticsearchOperations elasticsearchOperations;
  private final SearchRequestFactory searchRequestFactory;

  @Override
  public List<User> searchUsers(SearchRequestDto searchRequest) {
    NativeQuery nativeQuery = searchRequestFactory.build(searchRequest);
    SearchHits<User> search = elasticsearchOperations.search(nativeQuery, User.class);
    return search.getSearchHits().stream()
        .map(SearchHit::getContent)
        .collect(Collectors.toList());
  }
}
