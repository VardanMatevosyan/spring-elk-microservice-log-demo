package com.client.demoelklogclient.service;

import com.client.demoelklogclient.dto.request.SearchRequestDto;
import com.client.demoelklogclient.entity.User;
import java.util.Set;

public interface UserSearchService {

  Set<User> searchUsers(SearchRequestDto searchRequest);

}
