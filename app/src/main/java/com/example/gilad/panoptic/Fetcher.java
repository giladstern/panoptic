package com.example.gilad.panoptic;

import android.util.Log;
import android.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by Gilad on 8/11/2017.
 */
public class Fetcher {
    static String[] sources = {"the-new-york-times", "bbc-news", "cnn", "the-wall-street-journal","breitbart-news"};
    static String key = "f73616ed90dd4e0c8a9a81a05f04a575";
    static String first = "https://newsapi.org/v1/articles?source=";
    static String second = "&sortBy=top&apiKey=";

    static ArrayList<Article> collect(){
        ArrayList<Article> articles = new ArrayList<>();

        for (String source: sources) {
            try {
                URLConnection connection = new URL(first + source + second + key).openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String text = reader.readLine();

                JSONObject json = new JSONObject(text);

                JSONArray array = json.getJSONArray("articles");

                for (int i = 0; i < array.length(); i++){
                    JSONObject object = array.getJSONObject(i);

                    String title = object.getString("title");
                    String url = object.getString("url");
                    String img = object.getString("urlToImage");

                    Article toAdd = new Article(title, url, img, source);
                    articles.add(toAdd);
                }

            } catch (Exception e) {
                System.out.println(String.format("Exception: %s", e.getStackTrace()));
            }
        }

        ArrayList<Set<String>> tokenized = new ArrayList<>();

        Stemmer stemmer = new Stemmer();

        for (Article article: articles){
            String[] words = article.title.split("[^a-zA-Z0-9]");
            for (int i = 0; i < words.length; i++){
                words[i] = stemmer.stripAffixes(words[i]);
            }

            Set<String> toAdd = new HashSet<>();

            for (String word: words){
                toAdd.add(word);
            }

            tokenized.add(toAdd);
        }

        Map<Article, Pair<Article, Integer>> scores = new HashMap<>();

        for (Article first: articles){
            for (Article second: articles){

            }
        }

        String title = articles.get(0).title;

        System.out.println(title);



        String word = title.split("[^a-zA-Z0-9]")[5];

        System.out.println(stemmer.stripAffixes(word));

        return articles;
    }
}
