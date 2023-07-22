package com.client.demoelklogclient.entity;

import static org.springframework.data.elasticsearch.annotations.DateFormat.date_hour_minute_second;

import com.client.demoelklogclient.constant.Indices;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Dynamic;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Document(indexName = Indices.USER)
public class User {

  @Id
  @Field(type = FieldType.Keyword)
  Long id;

  @Field
  String username;

  @Field(type = FieldType.Keyword)
  String testField;

  @Field(type = FieldType.Text)
  String email;

  @Field(type = FieldType.Date, format = date_hour_minute_second)
  LocalDateTime created;

  @Field(type = FieldType.Boolean)
  Boolean updated;

  @Field(type = FieldType.Nested, dynamic = Dynamic.TRUE)
  List<LoggingData> loggingDatas = new ArrayList<>();
}


