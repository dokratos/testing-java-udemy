package com.appsdeveloperblog.estore.service;

import com.appsdeveloperblog.estore.model.User;

public interface EmailVerificationService {
    void scheduleEmailConfirmation(User user);
}
