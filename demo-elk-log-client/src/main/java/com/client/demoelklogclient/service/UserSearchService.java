package com.client.demoelklogclient.service;

import com.client.demoelklogclient.dto.request.SearchRequestDto;
import com.client.demoelklogclient.entity.User;
import java.util.List;

public interface UserSearchService {

  List<User> searchUsers(SearchRequestDto searchRequest);

}
