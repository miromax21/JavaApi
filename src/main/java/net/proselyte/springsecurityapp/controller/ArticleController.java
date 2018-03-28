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
    public ResponseEntity<Article> createArticle(@RequestBody Article article, UriComponentsBuilder builder) {
        Article newArticle = articleService.createArticle(article);
        if(newArticle.getArticleId() <0){
            ResponseEntity rval = null;
            switch (newArticle.getArticleId()) {
                case DAO_ALREADY_REPORTED:  rval = new ResponseEntity<>(HttpStatus.ALREADY_REPORTED); break;
//                case DAO_ALREADY_REPORTED:  rval = new ResponseEntity<Article>(article, HttpStatus.ALREADY_REPORTED); break;
                case DAO_CONNECT_ERROR:     rval =  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); break;
            }
            return rval;
        }
        return new ResponseEntity<Article>(newArticle, HttpStatus.OK);
    }
    @PostMapping("updateArticles")
    public ResponseEntity<List<Article>> updateArticles(@RequestBody List<Article> articles){
        articleService.updateArticles(articles);
        return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
    }

    @PutMapping("article")
    public ResponseEntity<Article> updateArticle(@RequestBody Article article){
        articleService.updateArticle(article);
        return new ResponseEntity<Article>(article, HttpStatus.OK);
    }

    @DeleteMapping("article")
    public ResponseEntity<Void> deleteArticle(@RequestParam("id") String id) {
        articleService.deleteArticle(Integer.parseInt(id));
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
