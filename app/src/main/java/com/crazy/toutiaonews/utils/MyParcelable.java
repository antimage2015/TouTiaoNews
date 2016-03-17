package com.crazy.toutiaonews.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyParcelable {

    //定义要被传输的数据
    public static String str1;

    public static List<String> str_urls = new ArrayList<>();

    public String getStr() {
        return str1;
    }

    public void setStr(String str) {
        str_urls.add(str);
        str1 = str;
//        Log.e(" setStr ", "  str_urls  " +str_urls.size());
 //       Log.e(" setStr ", "  str1  " +str1);
    }

    public MyParcelable() {
    }
}

