package com.hzh.fitness.service;

import com.hzh.fitness.po.Article;
import com.hzh.fitness.po.Comment;

/**
 * @author hzh
 */
public interface ArticleService {

    Article searchArticle(int id) throws Exception;

    Article createArticle(Article article) throws Exception;

    Article[] selectArticles(int id, String type, int page, int perPage) throws Exception;

    Article[] selectPersonalArticles(int id, int page, int perPage) throws Exception;

    Article[] selectFollowArticles(int id, int page, int perPage) throws Exception;

    Article[] selectNewestArticles(int page, int perPage) throws Exception;

    Article[] selectHotArticles(int page, int perPage) throws Exception;

    void likeArticle(int userId, int articleId) throws Exception;

    void likeComment(int userId, int commentId) throws Exception;

    Comment createComment(Comment comment) throws Exception;
}
