package com.hzh.fitness.mapper;

import com.hzh.fitness.po.Article;
import com.hzh.fitness.po.ArticleImg;
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

    int updateLikeCountToArticle(int id);

    int updateLikeCountToComment(int id);

    int insertLikeArticle(@Param("userId") int userId, @Param("articleId") int articleId);

    int insertLikeComment(@Param("userId") int userId, @Param("commentId") int commentId);

    int selectArticleLikeLog(@Param("userId") int userId, @Param("articleId") int articleId);

    int selectCommentLikeLog(@Param("userId") int userId, @Param("commentId") int commentId);

    int selectArticleId(int id);

    int selectCommentId(int id);
}
