package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.configuration.JwtUtils;
import com.openclassrooms.mddapi.configuration.ToolsUtils;
import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.service.ArticleService;
import com.openclassrooms.mddapi.service.UserCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserCommentController {

    private final UserCommentService userCommentService;
    private final ArticleService articleService;
    private final ToolsUtils toolsUtils;

    @Operation(
            summary = "Permet de faire un commentaire sur l'article concerné",
            description = "Pour faire un commentaire, il faut message, userId,articleId",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Détails de l'utilisateur à enregistrer",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = User.class),
                            examples = @ExampleObject(
                                    value = "{ \"content\": \"Coucou\", \"user_id\": 1, \"articleId\": 1 }"
                            )
                    )
            )
    )
    @PostMapping(value = "/articles/comment")
    public ResponseEntity<?> addComment(
            @RequestParam("articleId") Integer articleId,
            @RequestParam("content") String content
    ) {
        User user = toolsUtils.getUserLogin(JwtUtils.getAuthenticatedUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
        Article article = articleService.getArticleById(articleId);
        if (article == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Article not found");
        }
        userCommentService.addComment(content, user, article);
        return ResponseEntity.status(HttpStatus.OK).body("Comment added");
    }
}

