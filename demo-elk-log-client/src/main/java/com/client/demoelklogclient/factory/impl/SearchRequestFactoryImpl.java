package com.client.demoelklogclient.factory.impl;

import static java.util.Objects.nonNull;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.Query.Builder;
import co.elastic.clients.util.ObjectBuilder;
import com.client.demoelklogclient.dto.request.SearchRequestDto;
import com.client.demoelklogclient.factory.SearchRequestFactory;
import com.client.demoelklogclient.factory.SortFactory;
import java.util.Objects;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchRequestFactoryImpl implements SearchRequestFactory {

  SortFactory sortFactory;

  @Override
  public NativeQuery build(SearchRequestDto searchRequestDto) {
    if (Objects.isNull(searchRequestDto.getFields()) || searchRequestDto.getFields().isEmpty()) {
      return null;
    }
    if (hasNestedObjectPath(searchRequestDto)) {
      return buildNestedObjectQuery(searchRequestDto);
    }
    return buildSimpleQuery(searchRequestDto);
  }

  private boolean hasNestedObjectPath(SearchRequestDto dto) {
    return dto.getFields().iterator().next().split("\\.").length > 1;
  }

  private NativeQuery buildSimpleQuery(SearchRequestDto dto) {
    NativeQueryBuilder queryBuilder = NativeQuery.builder().withQuery(buildQueryFunction(dto));
    applySorting(dto, queryBuilder);
    return queryBuilder.build();
  }

  private NativeQuery buildNestedObjectQuery(SearchRequestDto dto) {
    String fieldName = dto.getFields().iterator().next();
    String parentPath = fieldName.substring(0, fieldName.indexOf("."));
    NativeQueryBuilder queryBuilder = NativeQuery.builder()
        .withQuery(q -> q
            .nested(nq -> nq
                .query(buildQueryFunction(dto))
                .path(parentPath)));
    applySorting(dto, queryBuilder);
    return queryBuilder.build();
  }

  private void applySorting(SearchRequestDto dto, NativeQueryBuilder queryBuilder) {
    if (nonNull(dto.getSortingDto())) {
      queryBuilder.withSort(sortFactory.build(dto.getSortingDto()));
    }
  }

  private Function<Builder, ObjectBuilder<Query>> buildQueryFunction(SearchRequestDto dto) {
    if (isMultiMatchRequest(dto)) {
      return query -> query
          .multiMatch(multiMatch -> multiMatch
              .fields(dto.getFields())
              .query(dto.getSearchTerm())
              .operator(Operator.And));
    }
    return query -> query
        .match(match -> match
            .field(dto.getFields().iterator().next())
            .query(dto.getSearchTerm())
            .operator(Operator.And));
  }

  private boolean isMultiMatchRequest(SearchRequestDto dto) {
    return dto.getFields().size() > 1;
  }
}
