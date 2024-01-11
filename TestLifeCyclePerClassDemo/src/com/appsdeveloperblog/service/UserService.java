package com.appsdeveloperblog.service;

import java.util.Map;

public interface UserService {
    String createUser(Map userDetails);
    Map updateUser(String userId, Map userDetails);
    Map getUserDetails(String userId);
    void deleteUser(String userId);
}
