package com.appsdeveloperblog.estore.data;

import com.appsdeveloperblog.estore.model.User;

public interface UsersRepository {
    boolean save(User user);
}
