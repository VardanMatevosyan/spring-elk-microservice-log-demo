package com.client.demoelklogclient.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.client.demoelklogclient.dto.request.SearchRequestDto;
import com.client.demoelklogclient.entity.User;
import com.client.demoelklogclient.factory.SearchRequestFactory;
import com.client.demoelklogclient.service.UserSearchService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSearchServiceImpl implements UserSearchService {

//  private final ElasticsearchClient client;
  private final ElasticsearchOperations elasticsearchOperations;
  private final SearchRequestFactory searchRequestFactory;

  @Override
  public Set<User> searchUsers(SearchRequestDto searchRequest) {
    NativeQuery nativeQuery = searchRequestFactory.build(searchRequest);
    SearchHits<User> search = elasticsearchOperations.search(nativeQuery, User.class);
    Set<User> users = search.getSearchHits().stream()
        .map(SearchHit::getContent)
        .collect(Collectors.toSet());

//    SearchResponse<User> response = client.search(searchRequestFactory.build(searchRequest), RequestOptions.DEFAULT);
//    List<Hit<User>> hits = response.hits().hits();
//    Set<User> users = hits.stream()
//        .map(Hit::source)
//        .collect(Collectors.toSet());
    return users;
  }
}
