package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.dao.interfaces.IArticleDAO;
import net.proselyte.springsecurityapp.model.Article;
import net.proselyte.springsecurityapp.service.interfaces.IArticleService;
import org.hibernate.criterion.NullExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import static net.proselyte.springsecurityapp.enums.Enums.*;

@Service
public class ArticleService implements IArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);
    @Autowired
    private IArticleDAO articleDAO;
    @Override
    public List<Article> getAllArticles() {
        try {
            return articleDAO.getAllArticles();
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("getAllArticles error: {}", e);
            return null;
        }
    }

    @Override
    public Article getArticleById(int articleId) {
        Article obj = null;
        try {
            obj = articleDAO.getArticleById(articleId);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.debug("getArticleById error: {}", e.getMessage());
        }
        return obj;
    }

    @Override
    public synchronized Article createArticle(Article article){
        Article new_article = new Article();
        try{
            if(articleDAO.articleExists(article.getTitle(),article.getCategory())){
                article.setArticleId(DAO_ALREADY_REPORTED);
                return new_article = article;
            }
            new_article = articleDAO.createArticle(article);
        } catch (Exception e) {
            logger.debug("createArticle error: {}", e);
            e.printStackTrace();
            article.setArticleId(DAO_CONNECT_ERROR);
            new_article = article;
        }
        finally {
            return  new_article;
        }
    }
    @Override
    public void updateArticle(Article article) {
        try {
            if(!articleDAO.articleExists(article.getTitle(),article.getCategory())){
               articleDAO.updateArticle(article);
            }
        } catch (Exception e) {
            logger.debug("updateArticle error: {}", e);
            e.printStackTrace();
        }
    }

    @Override
    public void updateArticles(List<Article> articles) {
        try{
            articleDAO.updateArticles(articles);
        } catch (Exception e) {
            logger.debug("updateArticles error: {}", e);
            e.printStackTrace();
        }
    }

    @Override
    public void deleteArticle(int articleId) {
        try {
            articleDAO.deleteArticle(articleId);
        } catch (Exception e) {
            logger.debug("deleteArticle error: {}", e);
            e.printStackTrace();
        }
    }
}
