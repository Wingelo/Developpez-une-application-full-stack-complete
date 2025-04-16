package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.configuration.JwtUtils;
import com.openclassrooms.mddapi.configuration.ToolsUtils;
import com.openclassrooms.mddapi.dto.ThemeDTO;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.service.ThemeService;
import com.openclassrooms.mddapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ThemeController {

    private final ThemeService themeService;
    private final ToolsUtils toolsUtils;
    private final UserService userService;

    @Operation(
            summary = "Récupérer tout les thèmes",
            description = "Permet de récuperer tout les thèmes de l'application"
    )
    @GetMapping("/themes")
    public ResponseEntity<?> getAllThemesInfos() {
        List<ThemeDTO> themeDTOS = themeService.getAllTheme();
        if (themeDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Themes not found");
        }
        return ResponseEntity.ok(themeDTOS);

    }


    @Operation(
            summary = "S'abonner à un thème",
            description = "Permet de ajouter un thème pour l'utilisateur du compte"
    )
    @PostMapping(value = "/themes/subscription/{themeId}")
    public ResponseEntity<?> addSubscriptionTheme(
            @PathVariable Integer themeId) {

        User user = toolsUtils.getUserLogin(JwtUtils.getAuthenticatedUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        Theme theme = themeService.findThemeById(themeId);
        if (theme == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theme not found");
        }

        if (user.getSubscriptions().contains(theme)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already subscribed to this theme");
        }

        userService.subscriptionTheme(user, theme);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/themes/subscription/{themeId}")
    public ResponseEntity<?> removeSubscriptionTheme(@PathVariable Integer themeId) {
        User user = toolsUtils.getUserLogin(JwtUtils.getAuthenticatedUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        Theme theme = themeService.findThemeById(themeId);
        if (!user.getSubscriptions().contains(theme)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not subscribed to this theme");
        }
        userService.removeSubscriptionTheme(user, theme);
        return ResponseEntity.ok().build();
    }
}
