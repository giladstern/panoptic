package com.example.gilad.panoptic;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Gilad on 8/18/2017.
 */
public class Article {
    public String title;
    public String source;
    public String url;
    public String img;

    public Article(String t, String s, String u, String i){
        this.title = t;
        this.source = s;
        this.url = u;
        this.img = i;
    }

    public Article(JSONObject object) throws JSONException{
        this.title = object.getString("title");
        this.source = object.getString("source");
        this.url = object.getString("url");
        this.img = object.getString("img");
    }

    public String toString(){
        return title;
    }
}
