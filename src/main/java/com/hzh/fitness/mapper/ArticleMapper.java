package com.hzh.fitness.mapper;

import com.hzh.fitness.po.Article;
import com.hzh.fitness.po.ArticleImg;
import com.hzh.fitness.po.Comment;
import org.apache.ibatis.annotations.Param;

/**
 * @author hzh
 */
public interface ArticleMapper {

    int selectImgCount(String hex);

    Article selectArticleById(int id);

    int insertArticle(Article article);

    int insertArticleImg(ArticleImg[] imgs);

    String selectImgHex(String imgHex);

    Article[] selectFollowArticles(@Param("id") int id, @Param("page") int page, @Param("perPage") int perPage);

    Article[] selectPersonalArticles(@Param("id") int id, @Param("page") int page, @Param("perPage") int perPage);

    Article[] selectNewestArticles(@Param("page") int page, @Param("perPage") int perPage);

    Article[] selectHotArticles(@Param("page") int page, @Param("perPage") int perPage);

    int selectTotalPages(int perPage);

    int updateLikeCountToArticle(@Param("id") int id, @Param("count") int count);

    int updateLikeCountToComment(@Param("id") int id, @Param("count") int count);

    int insertLikeArticle(@Param("userId") int userId, @Param("articleId") int articleId);

    int insertLikeComment(@Param("userId") int userId, @Param("commentId") int commentId);

    int deleteLikeArticle(@Param("userId") int userId, @Param("articleId") int articleId);

    int deleteLikeComment(@Param("userId") int userId, @Param("commentId") int commentId);

    int selectArticleLikeLog(@Param("userId") int userId, @Param("articleId") int articleId);

    int selectCommentLikeLog(@Param("userId") int userId, @Param("commentId") int commentId);

    int selectArticleId(int id);

    int selectCommentId(int id);

    int insertComment(Comment comment);

    int updateCommentCount(int id);

    Comment selectCommentById(int id);

    int checkArticleById(int id);

    int checkCommentById(int id);
}
