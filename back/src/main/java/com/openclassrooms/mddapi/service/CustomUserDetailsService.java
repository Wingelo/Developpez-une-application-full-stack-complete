package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        String identifier;

        if (user == null) {
            user = userRepository.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username or email: " + username);
            }
            identifier = user.getEmail(); // Use email if login was with email
        } else {
            identifier = user.getUsername(); // Use username if login was with username
        }

        return new org.springframework.security.core.userdetails.User(identifier, user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }
}
