package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.model.Article;
import net.proselyte.springsecurityapp.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;
import static net.proselyte.springsecurityapp.enums.Enums.*;
import java.util.List;
@Controller
@RequestMapping("/article")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("article")
    public ResponseEntity<Article> getArticleById(@RequestParam("id") String id){
        Article article = articleService.getArticleById(Integer.parseInt(id));
        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }

    @GetMapping("all-articles")
    public  ResponseEntity<List<Article>> getAllArticles(){
        List<Article> list = articleService.getAllArticles();
        if (list == null){
            return  new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<List<Article>>(list, HttpStatus.OK);
    }

    @PostMapping("article")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        Article newArticle = articleService.createArticle(article);
        ResponseEntity rval = null;
        int newArticleID = newArticle.getArticleId();
        if(newArticleID <0){
            article.setArticleId( -1 * newArticleID);
            switch (newArticleID) {
                case DAO_ALREADY_REPORTED:  rval =  ResponseEntity.status(HttpStatus.FORBIDDEN).body(article); break;
                case DAO_CONNECT_ERROR:     rval =  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); break;
            }
        }
        else {
            rval =  new ResponseEntity<Article>(newArticle, HttpStatus.OK);
        }
        return rval;
    }
    @PutMapping("updateArticles")
    public ResponseEntity<List<Article>> updateArticles(@RequestBody List<Article> articles){
        articleService.updateArticles(articles);
        return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
    }

    @PutMapping("article")
    public ResponseEntity<Article> updateArticle(@RequestBody Article article){
       Boolean Updated = articleService.updateArticle(article);
       if (Updated){
           return new ResponseEntity<Article>(article, HttpStatus.OK);
       }
       else {
           return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @DeleteMapping("article")
    public ResponseEntity<Void> deleteArticle(@RequestParam("id") String id) {
        articleService.deleteArticle(Integer.parseInt(id));
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
