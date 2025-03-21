package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;

import java.util.List;

public interface ArticleServiceImpl {

    Article getArticleById(Integer id);

    List<ArticleDTO> findAllArticles();

    void addArticle(String title, String content, Theme theme, User user);
}
