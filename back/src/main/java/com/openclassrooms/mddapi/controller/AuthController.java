package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.configuration.JwtUtils;
import com.openclassrooms.mddapi.configuration.ToolsUtils;
import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.service.AuthService;
import com.openclassrooms.mddapi.service.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final ThemeService themeService;
    private final ToolsUtils toolsUtils;

    @Operation(
            summary = "Enregistrer un nouvel utilisateur",
            description = "Permet à un utilisateur de s'enregistrer avec son email, son nom, et son mot de passe.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Détails de l'utilisateur à enregistrer",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = User.class),
                            examples = @ExampleObject(
                                    value = "{ \"email\": \"test@test5.com\", \"username\": \"test TEST\", \"password\": \"test!31\" }"
                            )
                    )
            )
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            Map<String, Object> authData = authService.register(user);
            return ResponseEntity.ok(authData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Authentifier un utilisateur",
            description = "Permet à un utilisateur de se connecter en utilisant son email et son mot de passe."
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("emailPseudo") String emailPseudo,
                                   @RequestParam("password") String password) {
        try {
            Map<String, Object> authData = authService.login(emailPseudo, password);
            return ResponseEntity.ok(authData);
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(
            summary = "Récuperer les informations de l'utilisateur connecté"
    )
    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo() {
        User user = toolsUtils.getUserLogin(JwtUtils.getAuthenticatedUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        List<ThemeDTO> themes = themeService.findThemesByUser(user);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("theme", themes);

        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/me/update")
    public ResponseEntity<?> updateUserInfo(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password) {

        String newToken = "";
        User user = toolsUtils.getUserLogin(JwtUtils.getAuthenticatedUsername());
        if (user != null) {
            try {
                newToken = authService.updateUserInfo(username, email, password, user);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        // Retourner le token et un message de succès
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User updated successfully");
        response.put("token", newToken);

        return ResponseEntity.ok(response);
    }
}
