package com.client.demoelklogclient.service;

import com.client.demoelklogclient.entity.User;

public interface UserLoggingService {

  void saveUser(User user);

  User getUser(Long id);
}
