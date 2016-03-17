package com.crazy.toutiaonews.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class JsonCache  {

    private static JsonCache jsonCache = null;

    private SharedPreferences preferences;

    private static String NAME = "com.crazy.toutiaonews.utils.JsonCache";

    private Context context;

    private JsonCache(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(NAME , 0);
    }

    public static JsonCache getInstance(Context context){
        if (jsonCache == null){
            return new JsonCache(context);
        } else {
            return jsonCache;
        }
    }

    public void setJsonCache(String key, String value){
        preferences.edit().putString(key, value).commit();
    }

    public String getJsonCache(String key){
        return preferences.getString(key, null);
    }

    public void clearCache(String key) {
        preferences.edit().remove(key).commit();
    }
}
