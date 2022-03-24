package com.hzh.fitness.service.impl;

import com.hzh.fitness.common.GlobalConstant;
import com.hzh.fitness.mapper.ArticleMapper;
import com.hzh.fitness.po.Article;
import com.hzh.fitness.po.ArticleImg;
import com.hzh.fitness.service.ArticleService;
import com.hzh.fitness.utils.FileUtils;
import org.apache.ibatis.annotations.Param;
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
        ArticleImg[] articleImgs = new ArticleImg[imgs.length];
        for (int i = 0; i < imgs.length; i++) {
            String hex = FileUtils.sha1String(imgData[i]);
            String suffix = "." + imgs[i].split("\\.")[1];
            if (articleMapper.selectImgHex(hex) == null)
                FileUtils.bytesToFile(imgData[i], GlobalConstant.IMAGE_ROOT + "/articleImg/", hex + suffix);
            articleImgs[i] = new ArticleImg(article.getId(), hex + suffix, FileUtils.sha1String(imgData[i]));
        }
        if (imgs.length > 0)
            articleMapper.insertArticleImg(articleImgs);
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
    public int likeArticle(int id) {
        return articleMapper.updateLikeCountToArticle(id);
    }

    @Override
    public int likeComment(int id) {
        return articleMapper.updateLikeCountToComment(id);
    }
}
