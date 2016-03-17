package com.crazy.toutiaonews.parsejson;

import java.util.List;

/**
 *  Created by scxh on 2016/1/11.
 */
public class StringT {
    private List<ImgextraT> imgextra;
    private List<AdsT> ads;               // 用于定义 HeaderList
    private String digest;    // 概述（title 下面部分）
    private String url;
    private String title;    // 标题
    private String replyCount; // 点击数量
    private String priority;  // 暂时还没有用到该属性
    private String source;   // 资源来源

    private String imgsrc;   // 图片来源

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public List<ImgextraT> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<ImgextraT> imgextra) {
        this.imgextra = imgextra;
    }

    public List<AdsT> getAds() {
        return ads;
    }

    public void setAds(List<AdsT> ads) {
        this.ads = ads;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
}
