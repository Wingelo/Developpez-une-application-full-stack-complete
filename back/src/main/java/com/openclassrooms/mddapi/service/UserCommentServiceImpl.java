package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.entity.User;

public interface UserCommentServiceImpl {

    void addComment(String comment, User user, Article article);
}
