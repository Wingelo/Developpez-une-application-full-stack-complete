package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.mddapi.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentDTO {

    private Integer id;
    private String content;
    private String createdAt;
    private String updatedAt;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("article_id")
    private Integer articleId;

    public static UserCommentDTO fromEntity(Comment comment) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new UserCommentDTO(
                comment.getId(),
                comment.getContent(),
                Optional.ofNullable(comment.getCreatedAt())
                        .map(date -> date.format(formatter))
                        .orElse(null),
                Optional.ofNullable(comment.getUpdatedAt())
                        .map(date -> date.format(formatter))
                        .orElse(null),
                comment.getUser().getId(),
                comment.getArticle().getId()

        );
    }


}
