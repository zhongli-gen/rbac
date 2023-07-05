package com.situ.rbac.service;

import com.situ.rbac.entity.User;

public interface UserService {
    String login(User user)throws Exception;
}
