package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.configuration.JwtUtils;
import com.openclassrooms.mddapi.configuration.ToolsUtils;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public Map<String, Object> register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email Already Exists");
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username Already Exists");
        }
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
        System.out.println(ToolsUtils.verifyStringWithRegex(user.getPassword(), regex));
        if (user.getPassword() == null || user.getPassword().isEmpty() || !ToolsUtils.verifyStringWithRegex(user.getPassword(), regex)) {
            throw new IllegalArgumentException("Invalid Password");
        }
        user.setRole(user.getRole() != null ? user.getRole() : "ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Map<String, Object> authData = new HashMap<>();
        authData.put("token", jwtUtils.generateToken(user.getEmail()));
        authData.put("type", "Bearer");

        return authData;
    }

    public Map<String, Object> login(String login, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));
            if (authentication.isAuthenticated()) {
                Map<String, Object> authData = new HashMap<>();
                authData.put("token", jwtUtils.generateToken(login));
                authData.put("type", "Bearer");
                return authData;
            } else {
                throw new AuthenticationException("Invalid identifiant or password") {
                };
            }
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Invalid identifiant or password") {
            };
        }
    }

    public void updateUserInfo(String username, String email, String password, User user) {
        if (!username.equals(user.getUsername()) && username != null && !username.isEmpty()) {
            User existingUser = userRepository.findByUsername(username);
            if (existingUser == null) {
                user.setUsername(username);
            } else {
                throw new IllegalArgumentException("Username already taken");
            }
        }

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.equals(user.getEmail()) && !email.isEmpty() && ToolsUtils.verifyStringWithRegex(email, emailRegex)) {
            User existingEmailUser = userRepository.findByEmail(email);
            if (existingEmailUser == null) {
                user.setEmail(email);
            } else {
                throw new IllegalArgumentException("Email already exists");
            }
        }

        if (password != null && !password.isEmpty()) {
            if (!password.equals(user.getPassword())) {
                String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
                if (!ToolsUtils.verifyStringWithRegex(password, regex)) {
                    throw new IllegalArgumentException("Invalid Password");
                }
                user.setPassword(passwordEncoder.encode(password));
            }
        }
        user.setUpdatedAt(LocalDateTime.now()); // Mise à jour de la date

        userRepository.save(user);
    }
}
