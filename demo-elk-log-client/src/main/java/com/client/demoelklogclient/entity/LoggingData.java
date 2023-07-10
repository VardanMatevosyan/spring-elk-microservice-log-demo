package com.client.demoelklogclient.entity;

import static org.springframework.data.elasticsearch.annotations.DateFormat.date_hour_minute_second;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoggingData {

  @Id
  @Field(type = FieldType.Keyword)
  Long id;

  @Field(type = FieldType.Date, format = date_hour_minute_second)
  LocalDateTime logTime;

  @Field(type = FieldType.Text)
  String message;

  @Field(type = FieldType.Text)
  String level;
}
