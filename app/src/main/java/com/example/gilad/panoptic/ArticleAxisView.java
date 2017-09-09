package com.example.gilad.panoptic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Raaz on 19/08/2017.
 * Creates one article axis, creates the article's circles.
 */

public class ArticleAxisView extends RelativeLayout{

    private Paint paint;
    private float cy;
    private List<ArticleCircle> articleCircles = new ArrayList<>();
    private int width = 0;
    private ArrayList<Float> rand = null;

    public ArticleAxisView(Context context) {
        this(context, null, null);
    }

    public ArticleAxisView(Context context, AttributeSet attrs) {

        this(context, attrs, null);

        List<Article> articles = new ArrayList<>();

        for (Article article: articles) {
                ArticleCircle circle = new ArticleCircle(context, "");
                this.articleCircles.add(circle);
        }

        for (ArticleCircle articleCircle: articleCircles){
            //Log.d("Debug","here");
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(50, 50);
            layoutParams.addRule(CENTER_VERTICAL|ALIGN_PARENT_LEFT);
            addView(articleCircle,layoutParams);
        }

    }

    public ArticleAxisView(Context context, AttributeSet attrs, Cluster cluster) {
        super(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(ContextCompat.getColor(context, R.color.grey));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        setWillNotDraw(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.cy = getPaddingTop() + (h - getPaddingTop() - getPaddingBottom()) / 2;

        width = w - getPaddingLeft() - getPaddingRight();

        updateAxisLayout();

    }

    private void updateAxisLayout() {
        int jump = width / (this.articleCircles.size() + 1);
        if (articleCircles.size() > 0) {
            Random r = new Random();
            if (rand == null) {
                rand = new ArrayList<>();
                for (int i = 0; i < articleCircles.size(); i++){
                    rand.add(r.nextFloat());
                }
            }
            LayoutParams layoutParams = new LayoutParams(50, 50);
            layoutParams.addRule(ALIGN_PARENT_LEFT | CENTER_VERTICAL);

            if (jump > 0) {
                // place circles
                for (int i = 0; i < articleCircles.size(); i++) {
                    int randomMovement = (int) (jump * (rand.get(i) / 2 - 0.25));
                    LayoutParams params = new LayoutParams(layoutParams);
                    params.leftMargin = (jump * (i + 1) + randomMovement);
                    articleCircles.get(i).setLayoutParams(params);
                }
            }
        }

        post(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        });
    }
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawLine(getPaddingLeft(), cy, getWidth() - getPaddingRight(), cy, paint);
    }

    public void updateAxisFromCluster(Cluster cluster, final Context context) {
        if (cluster != null && articleCircles != null){
            if (cluster.articles.size() != articleCircles.size()){
                rand = null;
            }
        }
        removeAllViews();
        articleCircles.clear();

        if (cluster != null) {
            // Create circles
            for (Article article : cluster.articles) {
                if (article.isDisplayed) {

                    ArticleCircle circle = new ArticleCircle(context, article.url);
                articleCircles.add(circle);
                circle.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent openArticle = new Intent(context, ArticleWebpage.class);
                        String url = ((ArticleCircle) view).getUrl();
                        openArticle.putExtra("url", url);
                        context.startActivity(openArticle);
                        }
                    });
                }
            }
        }

        for (ArticleCircle articleCircle: articleCircles){
            addView(articleCircle);
        }

        updateAxisLayout();

    }

}
