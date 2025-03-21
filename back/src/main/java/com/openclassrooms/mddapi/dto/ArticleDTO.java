package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    // ARTICLE
    private Integer id;

    private String title;
    private String content;

    private String createdAt;
    private String updatedAt;

    // THEME
    private String theme;

    // USER
    private String author;

    // Comment
    private List<UserCommentDTO> comments;

    public static ArticleDTO fromEntity(Article article) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                Optional.ofNullable(article.getCreatedAt())
                        .map(date -> date.format(formatter))
                        .orElse(null),
                Optional.ofNullable(article.getUpdatedAt())
                        .map(date -> date.format(formatter))
                        .orElse(null),
                article.getTheme().getName(),
                article.getUser().getUsername(),
                article.getComments() != null
                        ? article.getComments().stream().map(UserCommentDTO::fromEntity).collect(Collectors.toList())
                        : List.of()
        );
    }

}
