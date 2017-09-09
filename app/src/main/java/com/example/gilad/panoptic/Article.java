package com.example.gilad.panoptic;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Gilad on 8/18/2017.
 */
public class Article  implements Serializable{
    public String title;
    public String source;
    public String url;
    public String img;
    public Boolean isDisplayed;

    public Article(String title, String source, String url, String img){
        this.title = title;
        this.source = source;
        this.url = url;
        this.img = img;
        this.isDisplayed = true;
    }

    public Article(JSONObject object) throws JSONException{
        this.title = object.getString("title");
        this.source = object.getString("source");
        this.url = object.getString("url");
        this.img = object.getString("img");
        this.isDisplayed = true;
    }

    public String toString(){
        return title;
    }
}
