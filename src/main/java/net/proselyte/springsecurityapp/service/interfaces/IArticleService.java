package net.proselyte.springsecurityapp.service.interfaces;

import net.proselyte.springsecurityapp.model.Article;

import java.util.List;

public interface IArticleService {
    List<Article> getAllArticles();
    Article getArticleById(int articleId);
    Article createArticle(Article article) throws Exception;
    void updateArticle(Article article);
    void updateArticles(List<Article> article);
    void deleteArticle(int articleId);
}
