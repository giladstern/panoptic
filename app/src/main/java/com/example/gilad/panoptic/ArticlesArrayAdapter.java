package com.example.gilad.panoptic;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raaz on 19/08/2017.
 */

public class ArticlesArrayAdapter extends ArrayAdapter<Cluster> {

    Context context;
    int layoutResourceId;
    List<Cluster> articleClusters = null;


    public ArticlesArrayAdapter(@NonNull Context context, @LayoutRes int layoutResourceId, @NonNull List<Cluster> articleClusters) {
        super(context, layoutResourceId, articleClusters);
        this.articleClusters = articleClusters;
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
        Picasso.with(context).load(cluster.articles.get(0).img).resize(100,100).centerCrop().into(holder.thumbnail);
        holder.articleAxis.updateAxisFromCluster(cluster, context);

        return row;
    }

    static class ArticleViewHolder
    {
        ImageView thumbnail;
        TextView hashtags;
        ArticleAxisView articleAxis;
    }
}
