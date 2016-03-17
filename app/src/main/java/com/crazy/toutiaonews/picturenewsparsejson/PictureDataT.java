package com.crazy.toutiaonews.picturenewsparsejson;

import java.util.List;

/**
 * Created by scxh on 2016/1/13.
 */
public class PictureDataT {

    private String is_intro;
    private List<PictureListT> list;

    public String getIs_intro() {
        return is_intro;
    }

    public void setIs_intro(String is_intro) {
        this.is_intro = is_intro;
    }

    public List<PictureListT> getList() {
        return list;
    }

    public void setList(List<PictureListT> list) {
        this.list = list;
    }
}
