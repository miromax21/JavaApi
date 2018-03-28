package net.proselyte.springsecurityapp.dao.interfaces;
import net.proselyte.springsecurityapp.model.Article;

import java.sql.SQLException;
import java.util.List;

public interface IArticleDAO {
    List<Article> getAllArticles() throws Exception;
    Article getArticleById(int articleId) throws SQLException;
    Article createArticle(Article article) throws Exception;
    void updateArticle(Article article) throws Exception;
    void updateArticles(List<Article> article) throws Exception;
    void deleteArticle(int articleId) throws Exception;
    boolean articleExists(String title, String category) throws SQLException;
}