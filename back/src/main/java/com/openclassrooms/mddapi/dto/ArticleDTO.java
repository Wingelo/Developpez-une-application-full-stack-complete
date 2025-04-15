package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    // ARTICLE
    private Integer id;

    private String title;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // THEME
    private ThemeDTO theme;

    // USER
    private UserDetailsDTO user;
    // Comment
    private List<UserCommentDTO> comments;

    public static ArticleDTO fromEntity(Article article) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                ThemeDTO.fromEntity(article.getTheme()),
                UserDetailsDTO.fromEntity(article.getUser()),
                article.getComments() != null
                        ? article.getComments().stream().map(UserCommentDTO::fromEntity).collect(Collectors.toList())
                        : List.of()
        );
    }

}
