package com.client.demoelklogclient.entity;

import java.util.ArrayList;
import java.util.Collections;
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
@Document(indexName = "user")
public class User {

  @Id
  @Field(type = FieldType.Keyword)
  Long id;

  @Field(type = FieldType.Text)
  String username;

  @Field(type = FieldType.Text)
  String email;

  @Field(type = FieldType.Nested, dynamic = Dynamic.TRUE)
  List<LoggingData> loggingDatas = new ArrayList<>();
}


