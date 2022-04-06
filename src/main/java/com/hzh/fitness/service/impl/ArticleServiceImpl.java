package com.hzh.fitness.service.impl;

import com.hzh.fitness.common.GlobalConstant;
import com.hzh.fitness.exception.GlobalException;
import com.hzh.fitness.mapper.ArticleMapper;
import com.hzh.fitness.po.Article;
import com.hzh.fitness.po.ArticleImg;
import com.hzh.fitness.po.Comment;
import com.hzh.fitness.service.ArticleService;
import com.hzh.fitness.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author hzh
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public Article searchArticle(int id) throws Exception {
        return articleMapper.selectArticleById(id);
    }

    @Override
    public Article createArticle(Article article) throws Exception {
        byte[][] imgData = article.getImgData();
        String[] imgs = article.getImg();
        articleMapper.insertArticle(article);
        if (imgs != null) {
            ArticleImg[] articleImgs = new ArticleImg[imgs.length];
            for (int i = 0; i < imgs.length; i++) {
                String hex = FileUtils.sha1String(imgData[i]);
                String suffix = "." + imgs[i].split("\\.")[1];
                if (articleMapper.selectImgHex(hex) == null)
                    FileUtils.bytesToFile(imgData[i], GlobalConstant.IMAGE_ROOT + "/articleImg/", hex + suffix);
                articleImgs[i] = new ArticleImg(article.getId(), hex + suffix, FileUtils.sha1String(imgData[i]));
            }
            articleMapper.insertArticleImg(articleImgs);
        }
        return articleMapper.selectArticleById(article.getId());
    }

    @Override
    public Article[] selectArticles(int id, String type, int page, int perPage) throws Exception {
        return new Article[0];
    }

    @Override
    public Article[] selectPersonalArticles(int id, int page, int perPage) throws Exception {
        return articleMapper.selectPersonalArticles(id, page, perPage);
    }

    @Override
    public Article[] selectFollowArticles(int id, int page, int perPage) throws Exception {
        return articleMapper.selectFollowArticles(id, page, perPage);
    }

    @Override
    public Article[] selectNewestArticles(int page, int perPage) throws Exception {
        return articleMapper.selectNewestArticles(page, perPage);
    }

    @Override
    public Article[] selectHotArticles(int page, int perPage) throws Exception {
        return articleMapper.selectHotArticles(page, perPage);
    }

    @Override
    public void likeArticle(int userId, int articleId) throws Exception {
        int c = articleMapper.selectArticleLikeLog(userId, articleId);
        int c2 = articleMapper.selectArticleId(articleId);
        if (c == 1) {
            throw new GlobalException("已经点赞过了");
        }
        if (c2 == 0) {
            throw new GlobalException("不存在该动态");
        }
        articleMapper.updateLikeCountToArticle(articleId, 1);
        articleMapper.insertLikeArticle(userId, articleId);
    }

    @Override
    public void likeComment(int userId, int commentId) throws Exception {
        int c = articleMapper.selectCommentLikeLog(userId, commentId);
        int c2 = articleMapper.selectCommentId(commentId);
        if (c == 1) {
            throw new GlobalException("已经点赞过了");
        }
        if (c2 == 0) {
            throw new GlobalException("不存在该评论");
        }
        articleMapper.insertLikeComment(userId, commentId);
        articleMapper.updateLikeCountToComment(commentId, 1);
    }

    @Override
    public void dislikeArticle(int userId, int articleId) throws Exception {
        int c = articleMapper.selectArticleLikeLog(userId, articleId);
        int c2 = articleMapper.selectArticleId(articleId);
        if (c == 0) {
            throw new GlobalException("还未点赞过");
        }
        if (c2 == 0) {
            throw new GlobalException("不存在该动态");
        }
        articleMapper.updateLikeCountToArticle(articleId, -1);
        articleMapper.deleteLikeArticle(userId, articleId);
    }

    @Override
    public void dislikeComment(int userId, int commentId) throws Exception {
        int c = articleMapper.selectCommentLikeLog(userId, commentId);
        int c2 = articleMapper.selectCommentId(commentId);
        if (c == 0) {
            throw new GlobalException("还未点赞过");
        }
        if (c2 == 0) {
            throw new GlobalException("不存在该评论");
        }
        articleMapper.deleteLikeComment(userId, commentId);
        articleMapper.updateLikeCountToComment(commentId, -1);
    }

    @Override
    public Comment createComment(Comment comment) throws Exception {
        if (comment.getCommentId() != null && articleMapper.checkArticleById(comment.getCommentId()) == 0) {
            throw new GlobalException("不存在该评论");
        }
        if (articleMapper.checkArticleById(comment.getArticleId()) == 0) {
            throw new GlobalException("不存在该动态");
        }
        articleMapper.insertComment(comment);
        articleMapper.updateCommentCount(comment.getArticleId());
        return articleMapper.selectCommentById(comment.getId());
    }
}
