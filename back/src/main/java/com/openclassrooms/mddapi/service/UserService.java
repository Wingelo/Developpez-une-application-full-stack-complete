package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceImpl {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void subscriptionTheme(User user, Theme theme) {
        user.getSubscriptions().add(theme);
        userRepository.save(user);
    }

    public void removeSubscriptionTheme(User user, Theme theme) {
        user.getSubscriptions().remove(theme);
        userRepository.save(user);
    }
}
