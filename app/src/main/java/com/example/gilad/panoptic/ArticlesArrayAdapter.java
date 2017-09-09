package com.example.gilad.panoptic;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Raaz on 19/08/2017.
 */

public class ArticlesArrayAdapter extends ArrayAdapter<Cluster> implements Filterable {

    private Context context;
    private int layoutResourceId;
    private List<Cluster> unfiltered = null;
    private List<Cluster> articleClusters = null;
    private ArticleFilter filter = new ArticleFilter();
    private List<String> sources = new ArrayList<>();

    public ArticlesArrayAdapter(@NonNull Context context, @LayoutRes int layoutResourceId, @NonNull List<Cluster> articleClusters) {
        super(context, layoutResourceId, articleClusters);
        this.articleClusters = articleClusters;
        this.unfiltered = articleClusters;
        this.context = context;
        this.layoutResourceId = layoutResourceId;
    }

    private String tagsToString(List<String> tags) {
        String tagsString =  "";
        for (String tag: tags) {
            tagsString += "#" + tag + " ";
        }
        return tagsString;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ArticleViewHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ArticleViewHolder();
            holder.thumbnail = (ImageView)row.findViewById(R.id.thumbnail);
            holder.hashtags = (TextView)row.findViewById(R.id.hashtags);
            holder.hashtags.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.articleAxis = (ArticleAxisView)row.findViewById(R.id.axis);

            row.setTag(holder);
        }
        else
        {
            holder = (ArticleViewHolder)row.getTag();
        }

        Cluster cluster = articleClusters.get(position);
        holder.hashtags.setText(tagsToString(cluster.tags));
        if (cluster.articles.size() > 0) {
            Picasso.with(context).load(cluster.articles.get(0).img).resize(100, 100).centerCrop().into(holder.thumbnail);
        }
        holder.articleAxis.updateAxisFromCluster(cluster, context);

        return row;
    }

    static class ArticleViewHolder
    {
        ImageView thumbnail;
        TextView hashtags;
        ArticleAxisView articleAxis;
    }

    public Filter getFilter() {
        return filter;
    }

    public class ArticleFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            Log.d("DEBUG", "in performFiltering");
            Log.d("DEBUG", "results: " + unfiltered);
            FilterResults filterResults = new FilterResults();

            final List<Cluster> res = new ArrayList<>();

            for (Cluster cluster: unfiltered){
                for (String tag : cluster.tags){
                    if (tag.startsWith((String) constraint)){
                        res.add(cluster);
                        break;
                    }
                }
            }

            // go over returned clusters and re-build them with only valid news sources
            for (Cluster cluster: res){
                List<Article> newArticles = new ArrayList<>();
                for (Article article: cluster.articles)
                {
                    Log.d("Source", "checking article");
                    Log.d("Source", "sources is: " + sources);
                    if (!sources.contains(article.source)) {
                        Log.d("Source", "Adding article with source: " + article.source);
                        newArticles.add(article);
                    }
                }
                cluster.articles = newArticles;
            }

            final List<Cluster> finalRes = new ArrayList<>();

            for (Cluster cluster: res){
                if(cluster.articles.size() > 0) {
                    finalRes.add(cluster);
                }

            }


            filterResults.values = finalRes;
            filterResults.count = finalRes.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            articleClusters = (List<Cluster>) results.values;
            notifyDataSetChanged();
        }


    }

    public void updateSources(List<String> NewSources){
        this.sources = NewSources;

    }

    public void updateClusters(List<Cluster> clusters) {
        this.articleClusters = clusters;
    }

    @Override
    public int getCount() {
        if (articleClusters != null) return articleClusters.size();
        else return 0;
    }
}
