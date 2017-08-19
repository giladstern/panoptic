package com.example.gilad.panoptic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.lambdainvoker.*;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Exchanger;


public class Test extends AppCompatActivity {

    List<Cluster> clusters = null;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }



//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putSerializable("clusters", (Serializable) clusters);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.drawable.ic_top_menu_logo);
        setSupportActionBar(myToolbar);

//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
//
//        if(savedInstanceState != null && savedInstanceState.getSerializable("clusters") != null) {
//
//            clusters = (List<Cluster>) savedInstanceState.getSerializable("clusters");
//            ArticlesArrayAdapter adapter = new ArticlesArrayAdapter(Test.this, R.layout.list_row, clusters);
//
//            ListView articlesListView = (ListView) findViewById(R.id.articles_list_view);
//
//            articlesListView.setAdapter(adapter);
//
//        } else {
//            // Create an instance of CognitoCachingCredentialsProvider
//            CognitoCachingCredentialsProvider cognitoProvider = new CognitoCachingCredentialsProvider(
//                    this.getApplicationContext(), "us-east-2:fd992983-b013-4647-82be-85cafbc04dd5", Regions.US_EAST_2);
//
//            // Create LambdaInvokerFactory, to be used to instantiate the Lambda proxy.
//            LambdaInvokerFactory factory = new LambdaInvokerFactory(this.getApplicationContext(),
//                    Regions.US_EAST_2, cognitoProvider);
//
//            // Create the Lambda proxy object with a default Json data binder.
//            // You can provide your own data binder by implementing
//            // LambdaDataBinder.
//            final MyInterface myInterface = factory.build(MyInterface.class);
//
//            RequestClass request = new RequestClass("John", "Doe");
//            // The Lambda function invocation results in a network call.
//            // Make sure it is not called from the main thread.
//            new AsyncTask<RequestClass, Void, ResponseClass>() {
//                @Override
//                protected ResponseClass doInBackground(RequestClass... params) {
//                    // invoke "echo" method. In case it fails, it will throw a
//                    // LambdaFunctionException.
//                    try {
//                        return myInterface.AndroidBackendLambdaFunction(params[0]);
//                    } catch (LambdaFunctionException lfe) {
//                        Log.e("Tag", "Failed to invoke echo", lfe);
//                        return null;
//                    }
//                }
//
//                @Override
//                protected void onPostExecute(ResponseClass result) {
                    String data = getIntent().getStringExtra("data");
                    if (data == "") {
                        return;
                    }
//
                    try {
                        clusters = Cluster.parseClusters(data);
                    } catch (JSONException e) {
                        Log.d("Debug", e.getMessage());
                    }
//
//                    if (clusters != null) {
//                        Log.d("Debug", clusters.toString());
//                    } else {
//                        Log.d("Debug", "NULL!");
//                    }

                    ArticlesArrayAdapter adapter = new ArticlesArrayAdapter(Test.this, R.layout.list_row, clusters);

                    ListView articlesListView = (ListView) findViewById(R.id.articles_list_view);

                    articlesListView.setAdapter(adapter);



 //               }
 //           }.execute(request);
 //       }
    }
}
