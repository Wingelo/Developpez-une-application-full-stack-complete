package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ArticleDTO;
import com.openclassrooms.mddapi.entity.Article;
import com.openclassrooms.mddapi.entity.Theme;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService implements ArticleServiceImpl {

    private final ArticleRepository articleRepository;

    public Article getArticleById(Integer id) {
        return articleRepository.findById(id).orElse(null);
    }

    public List<ArticleDTO> findAllArticles() {
        List<Article> articlesList = articleRepository.findAll();
        return articlesList.stream()
                .map(ArticleDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void addArticle(String title, String content, Theme theme, User user) {
        Article article = new Article();
        if (title != null) {
            article.setTitle(title);
        }
        if (content != null) {
            article.setContent(content);
        }
        article.setCreatedAt(LocalDateTime.now());
        article.setTheme(theme);
        article.setUser(user);
        articleRepository.save(article);
    }

}
