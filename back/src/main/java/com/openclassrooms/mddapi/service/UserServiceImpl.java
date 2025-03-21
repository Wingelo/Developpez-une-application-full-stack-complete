package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;

public interface UserServiceImpl {

    User findByEmail(String email);

    User findByUsername(String username);

    void subscriptionTheme(User user, Theme theme);

    void removeSubscriptionTheme(User user, Theme theme);

}
