package com.crazy.toutiaonews.picturenewsparsejson;

import java.util.List;

/**
 * Created by scxh on 2016/1/13.
 */
public class PicturePicsT {

    private String total;
    // pics 总数的图片
    private List<PicturePicsListT> list;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<PicturePicsListT> getList() {
        return list;
    }

    public void setList(List<PicturePicsListT> list) {
        this.list = list;
    }
}
