package com.crazy.toutiaonews.picturenewsparsejson;

/**
 *  Created by scxh on 2016/1/13.
 */
public class PictureListT {

    private String title;
    // 图片来源
    private String pic;
    // 包含了 pics 的总数
    private PicturePicsT pics;
    // 下面包含跟帖数量
    private PictureGenTieT comment_count_info;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public PicturePicsT getPics() {
        return pics;
    }

    public void setPics(PicturePicsT pics) {
        this.pics = pics;
    }

    public PictureGenTieT getComment_count_info() {
        return comment_count_info;
    }

    public void setComment_count_info(PictureGenTieT comment_count_info) {
        this.comment_count_info = comment_count_info;
    }
}
