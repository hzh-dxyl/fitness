package com.hzh.fitness.po;


/**
 * @author hzh
 */
public class User {

    private int id = 0;
    private String phone;
    private String nickname;
    private String img;
    private String gender;
    private String birth;
    /**
     * 简介
     */
    private String intro;
    /**
     * 头像文件sha1哈希码
     */
    private String imgHex;
    /**
     * 密码的sha1哈希码
     */
    private String pwdHex;
    /**
     * 关注该user的用户
     */
    private User[] followers;
    /**
     * 该user关注的用户
     */
    private User[] focus;
    /**
     * 该user的动态前10条
     */
    private Article[] articles;

    public User[] getFollowers() {
        return followers;
    }

    public void setFollowers(User[] followers) {
        this.followers = followers;
    }

    public User[] getFocus() {
        return focus;
    }

    public void setFocus(User[] focus) {
        this.focus = focus;
    }

    public Article[] getArticles() {
        return articles;
    }

    public void setArticles(Article[] articles) {
        this.articles = articles;
    }

    public User(String phone, String pwdHex) {
        this.phone = phone;
        this.pwdHex = pwdHex;
    }

    public User(int id) {
        this.id = id;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImgHex() {
        return imgHex;
    }

    public void setImgHex(String imgHex) {
        this.imgHex = imgHex;
    }

    public String getPwdHex() {
        return pwdHex;
    }

    public void setPwdHex(String pwdHex) {
        this.pwdHex = pwdHex;
    }
}
