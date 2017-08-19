package com.example.gilad.panoptic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gilad on 8/18/2017.
 */
public class Cluster implements Serializable{
    List<Article> articles;
    List<String> tags;

    public Cluster(JSONObject object) throws JSONException{
        this.articles = new ArrayList<>();
        this.tags = new ArrayList<>();

        JSONArray tagArray = object.getJSONArray("tags");
        for (int i = 0; i < tagArray.length(); i++){
            this.tags.add(tagArray.getString(i));
        }

        JSONArray clusterArray = object.getJSONArray("cluster");
        for (int i = 0; i < clusterArray.length(); i++){
            this.articles.add(new Article(clusterArray.getJSONObject(i)));
        }
    }

    public static List<Cluster> parseClusters(String data) throws JSONException{
        data = data.replace("u'", "'").replace("u\"","\"");
        JSONArray array = new JSONArray(data);
        return parseClusters(array);
    }

    public static List<Cluster> parseClusters(JSONArray array) throws JSONException{
        List<Cluster> retVal = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            retVal.add(new Cluster(array.getJSONObject(i)));
        }
        return retVal;
    }

    public String toString(){
        return articles.toString();
    }
}
