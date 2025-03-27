package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.configuration.JwtUtils;
import com.openclassrooms.mddapi.configuration.ToolsUtils;
import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.ThemeService;
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
public class ArticleController {

    private final ArticleService articleService;
    private final ThemeService themeService;
    private final ToolsUtils toolsUtils;


    @Operation(
            summary = "Récupérer tous les articles",
            description = "Permet de récuperer tous les articles de tous les utilisateurs"
    )
    @GetMapping("/articles")
    public ResponseEntity<?> getAllArticles() {
        List<ArticleDTO> articleDTOS = articleService.findAllArticles();
        if (articleDTOS.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
        }
        return ResponseEntity.ok(articleDTOS);

    }

    @Operation(
            summary = "Récupere un seul article",
            description = "Permet de récupérer un seul article, exemple : article/1"

    )
    @GetMapping("/articles/{id}")
    public ResponseEntity<?> getArticleInfo(@PathVariable int id) {
        Article article = articleService.getArticleById(id);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found");
        }

        ArticleDTO articleDTO = ArticleDTO.fromEntity(article);

        return ResponseEntity.ok(articleDTO);
    }


    @Operation(
            summary = "Ajoute un nouvel article",
            description = "Permet de ajouter un article pour l'utilisateur du compte"
    )
    @PostMapping(value = "/articles")
    public ResponseEntity<?> addArticle(
            @RequestParam("themeId") Integer themeId,
            @RequestParam("title") String title,
            @RequestParam("content") String content) {

        // Récupérez l'utilisateur correspondant à l'email
        User user = toolsUtils.getUserLogin(JwtUtils.getAuthenticatedUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Theme theme = themeService.findThemeById(themeId);
        if (theme == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theme not found");
        }

        articleService.addArticle(title, content, theme, user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Article created !");
    }

}
