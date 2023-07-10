package com.client.demoelklogclient.service.impl;

import com.client.demoelklogclient.entity.User;
import com.client.demoelklogclient.repository.elastic.UserRepository;
import com.client.demoelklogclient.service.UserLoggingService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoggingServiceImpl implements UserLoggingService {

  private final UserRepository userRepository;


  @Override
  public void saveUser(User user) {
    userRepository.save(user);
  }

  @Override
  public User getUser(Long id) {
    return userRepository.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Can't found user by id %s".formatted(id)));
  }
}
