package com.hzh.fitness.controller;

import com.alibaba.fastjson.JSONObject;
import com.hzh.fitness.common.MyResponse;
import com.hzh.fitness.exception.GlobalException;
import com.hzh.fitness.po.Article;
import com.hzh.fitness.po.Comment;
import com.hzh.fitness.service.ArticleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author hzh
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    protected static Log logger = LogFactory.getLog(ArticleController.class);

    @Autowired
    ArticleService articleService;

    @GetMapping("/{id}")
    public MyResponse<Article> getArticle(@PathVariable int id) throws Exception {
        Article article = articleService.searchArticle(id);
        if (article == null) {
            return MyResponse.createResponseByError("没有该动态");
        }
        return MyResponse.createResponseBySuccess("success", article);
    }

    @PostMapping("")
    public MyResponse<Article> createArticleWithToken(@RequestBody JSONObject data) throws Exception {
        Article article = JSONObject.toJavaObject(data, Article.class);
        //需要的参数：userId,text,img[],imgData[],isShare,shareArticle
        if (article.getImgData() != null && article.getImg() == null) {
            throw new GlobalException("没有图片名信息！");
        }
        if (article.getIsShare() == 1 && article.getShareArticleId() == 0) {
            throw new GlobalException("没有被分享动态信息");
        }
        if (article.getUserId() == 0) {
            throw new GlobalException("没有发动态者信息");
        }
        article = articleService.createArticle(article);
        return MyResponse.createResponseBySuccess("success", article);
    }

    //要有分页
    @GetMapping("/{id}/{type}")
    public MyResponse<JSONObject> getArticleListWithToken(@PathVariable int id, @PathVariable String type, int page, int perPage) throws Exception {
        JSONObject object = new JSONObject();
        Article[] articles;
        switch (type) {
            case "follow":
                articles = articleService.selectFollowArticles(id, page, perPage);
                break;
            case "newest":
                articles = articleService.selectNewestArticles(page, perPage);
                break;
            case "hot":
                articles = articleService.selectHotArticles(page, perPage);
                break;
            case "personal":
                articles = articleService.selectPersonalArticles(id, page, perPage);
                break;
            default:
                throw new GlobalException("错误的类型信息");
        }
        object.put("length", articles.length);
        object.put("articles", articles);
        object.put("page", page);
        object.put("perPage", perPage);
        object.put("type", type);
        return MyResponse.createResponseBySuccess("success", object);
    }

    @PatchMapping("/{id}/like/{userId}")
    public MyResponse<JSONObject> likeArticleWithToken(@PathVariable int id, @PathVariable int userId) throws Exception {
        articleService.likeArticle(userId, id);
        return MyResponse.createResponseBySuccess("success");
    }

    @PatchMapping("/comments/{id}/like/{userId}")
    public MyResponse<JSONObject> likeCommentWithToken(@PathVariable int id, @PathVariable int userId) throws Exception {
        articleService.likeComment(userId, id);
        return MyResponse.createResponseBySuccess("success");
    }

    @PatchMapping("/{id}/dislike/{userId}")
    public MyResponse<JSONObject> dislikeArticleWithToken(@PathVariable int id, @PathVariable int userId) throws Exception {
        articleService.dislikeArticle(userId, id);
        return MyResponse.createResponseBySuccess("success");
    }

    @PatchMapping("/comments/{id}/dislike/{userId}")
    public MyResponse<JSONObject> dislikeCommentWithToken(@PathVariable int id, @PathVariable int userId) throws Exception {
        articleService.dislikeComment(userId, id);
        return MyResponse.createResponseBySuccess("success");
    }

    @PostMapping("/comments")
    public MyResponse<Comment> createCommentWithToken(@RequestBody Comment comment) throws Exception {
        comment = articleService.createComment(comment);
        return MyResponse.createResponseBySuccess("success", comment);
    }

    @GetMapping("/comments/{id}/subComment")
    public MyResponse<JSONObject> getSubComment(@PathVariable int id) throws Exception {
        JSONObject object = new JSONObject();
        Comment[] comments = articleService.getSubComment(id);
        object.put("subComments", comments);
        object.put("length", comments.length);
        object.put("id", id);
        return MyResponse.createResponseBySuccess("success", object);
    }

}
