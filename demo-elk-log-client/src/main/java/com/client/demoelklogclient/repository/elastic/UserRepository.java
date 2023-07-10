package com.client.demoelklogclient.repository.elastic;


import com.client.demoelklogclient.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserRepository extends ElasticsearchRepository<User, Long> {

}
