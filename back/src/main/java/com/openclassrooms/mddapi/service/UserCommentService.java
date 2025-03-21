package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.entity.Comment;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.UserCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommentService implements UserCommentServiceImpl {

    private final UserCommentRepository userCommentRepository;

    public void addComment(String commentUser, User user, Article article) {
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setArticle(article);
        comment.setContent(commentUser);
        userCommentRepository.save(comment);
    }
}
