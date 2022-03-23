package com.hzh.fitness.po;

public class ArticleImg {
    private int id;
    private int articleId;
    private String imgName;
    private String hex;

    public ArticleImg(int articleId, String imgName, String hex) {
        this.articleId = articleId;
        this.imgName = imgName;
        this.hex = hex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }
}
