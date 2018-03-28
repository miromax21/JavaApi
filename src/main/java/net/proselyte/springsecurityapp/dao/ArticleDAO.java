package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.dao.interfaces.IArticleDAO;
import net.proselyte.springsecurityapp.model.Article;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class ArticleDAO implements IArticleDAO {

    @PersistenceContext
    private  EntityManager entityManager;

    @Override
    public Article getArticleById(int articleId) throws SQLException{
        return entityManager.find(Article.class, articleId);
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Article> getAllArticles() throws SQLException {
        String hql = "FROM Article as atcl ORDER BY atcl.articleId DESC";
        return (List<Article>) entityManager.createQuery(hql.toString()).getResultList();
    }

    @Override
    public Article createArticle(Article article) throws SQLException {
        entityManager.persist(article);
        return article;
    }

    @Override
    public void updateArticle(Article article) throws SQLException {
        if (this.getArticleById(article.getArticleId()) != null){
            Article artcl = getArticleById(article.getArticleId());
            artcl.setTitle(article.getTitle());
            artcl.setCategory(article.getCategory());
            entityManager.flush();
        }
    }

    @Override
    public void updateArticles(List<Article> articleList) throws Exception {
        for(Article article: articleList){
            this.updateArticle(article);
        }
    }

    @Override
    public void deleteArticle(int articleId) throws SQLException {
        entityManager.remove(getArticleById(articleId));
    }

    @Override
    public int articleIdExists(String title, String category) throws SQLException {
        String hql = "FROM Article as atcl WHERE atcl.title = ? and atcl.category = ?";
        List<Article>  articlesList = entityManager.createQuery(hql.toString()).setParameter(1, title)
                .setParameter(2, category).getResultList();
        int articleID = articlesList.size() == 1 ? articlesList.get(0).getArticleId() : 0;
        return articleID;
    }
}
