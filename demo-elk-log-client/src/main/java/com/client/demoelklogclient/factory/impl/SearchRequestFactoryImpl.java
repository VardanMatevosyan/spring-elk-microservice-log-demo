package com.client.demoelklogclient.factory.impl;

import static java.util.Objects.nonNull;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.Query.Builder;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.util.ObjectBuilder;
import com.client.demoelklogclient.dto.request.FilteringDto;
import com.client.demoelklogclient.dto.request.RangeDto;
import com.client.demoelklogclient.dto.request.SearchRequestDto;
import com.client.demoelklogclient.factory.SearchRequestFactory;
import com.client.demoelklogclient.factory.SortFactory;
import java.util.Collection;
import java.util.List;
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

  // todo maybe should use chane here
  @Override
  public NativeQuery build(SearchRequestDto searchRequestDto) {
    if (isFullTextSearchAllowed(searchRequestDto)) {
      return buildFullTextSearchQuery(searchRequestDto);
    } else {
      return buildFilterSearchQuery(searchRequestDto);
    }
  }

  private NativeQuery buildFilterSearchQuery(SearchRequestDto dto) {
    NativeQueryBuilder queryBuilder = NativeQuery.builder()
        .withQuery(query -> query
            .matchAll(ma -> ma));
    applyFiltering(dto.getFilteringDto(), queryBuilder);
    return queryBuilder.build();
  }

  private NativeQuery buildFullTextSearchQuery(SearchRequestDto searchRequestDto) {
    if (hasNestedObjectPath(searchRequestDto.getFields())) {
      return buildNestedObjectQuery(searchRequestDto);
    }
    return buildSimpleQuery(searchRequestDto);
  }

  private static boolean isFullTextSearchAllowed(SearchRequestDto searchRequestDto) {
    List<String> fields = searchRequestDto.getFields();
    return nonNull(fields) && !fields.isEmpty();
  }


  private NativeQuery buildSimpleQuery(SearchRequestDto dto) {
    NativeQueryBuilder queryBuilder = NativeQuery.builder().withQuery(buildQueryFunction(dto));
    applySorting(dto, queryBuilder);
    applyFiltering(dto.getFilteringDto(), queryBuilder);
    return queryBuilder.build();
  }

  private void applyFiltering(FilteringDto dto, NativeQueryBuilder queryBuilder) {
    if (nonNull(dto)) {
      filterByDateRange(dto.getRangeDto(), queryBuilder);
    }
  }

  private void filterByDateRange(RangeDto dto, NativeQueryBuilder queryBuilder) {
    if (nonNull(dto)) {
      queryBuilder
          .withFilter(fq -> fq
              .bool(bq -> bq
                  .must(mq -> buildMustQueryBuilder(dto, mq))));
    }
  }

  private ObjectBuilder<Query> buildMustQueryBuilder(RangeDto dto, Builder mq) {
    if (hasNestedObjectPath(List.of(dto.getField()))) {
      String parentPath = dto.getField().substring(0, dto.getField().indexOf("."));
      return mq
          .nested(nq -> nq
              .path(parentPath)
              .query(q -> q
                  .range(rq -> buildRangeQueryBuilder(rq, dto))));
    }
    return mq.range(rq -> buildRangeQueryBuilder(rq, dto));
  }

  private ObjectBuilder<RangeQuery> buildRangeQueryBuilder(RangeQuery.Builder rq, RangeDto dto) {
    return rq
        .field(dto.getField())
        .from(dto.getFrom())
        .to(dto.getTo());
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
    applyFiltering(dto.getFilteringDto(), queryBuilder);
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

  private boolean hasNestedObjectPath(Collection<String> fieldPaths) {
    return fieldPaths
        .stream()
        .map(field -> field.split("\\."))
        .anyMatch(fields -> fields.length > 1);
  }

}
