package com.example.gilad.panoptic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseLongArray;
import android.widget.ListView;
import android.os.StrictMode;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.amazonaws.regions.Regions;

import org.json.JSONException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raaz on 19/08/2017.
 */
public class SplashActivity extends AppCompatActivity {

    List<Cluster> clusters = null;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("clusters", (Serializable) clusters);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        if (savedInstanceState != null && savedInstanceState.getSerializable("clusters") != null) {

            clusters = (List<Cluster>) savedInstanceState.getSerializable("clusters");
            ArticlesArrayAdapter adapter = new ArticlesArrayAdapter(SplashActivity.this, R.layout.list_row, clusters);

            ListView articlesListView = (ListView) findViewById(R.id.articles_list_view);

            articlesListView.setAdapter(adapter);

        } else {
            // Create an instance of CognitoCachingCredentialsProvider
            CognitoCachingCredentialsProvider cognitoProvider = new CognitoCachingCredentialsProvider(
                    this.getApplicationContext(), "us-east-2:fd992983-b013-4647-82be-85cafbc04dd5", Regions.US_EAST_2);

            // Create LambdaInvokerFactory, to be used to instantiate the Lambda proxy.
            LambdaInvokerFactory factory = new LambdaInvokerFactory(this.getApplicationContext(),
                    Regions.US_EAST_2, cognitoProvider);

            // Create the Lambda proxy object with a default Json data binder.
            // You can provide your own data binder by implementing
            // LambdaDataBinder.
            final MyInterface myInterface = factory.build(MyInterface.class);

            RequestClass request = new RequestClass("John", "Doe");
            // The Lambda function invocation results in a network call.
            // Make sure it is not called from the main thread.
            new AsyncTask<RequestClass, Void, ResponseClass>() {
                @Override
                protected ResponseClass doInBackground(RequestClass... params) {
                    // invoke "echo" method. In case it fails, it will throw a
                    // LambdaFunctionException.
                    try {
                        return myInterface.AndroidBackendLambdaFunction(params[0]);
                    } catch (LambdaFunctionException lfe) {
                        Log.e("Tag", "Failed to invoke echo", lfe);
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(ResponseClass result) {
                    if (result == null) {
                        Intent mainClass = new Intent(SplashActivity.this, Test.class);
                        mainClass.putExtra("data", "");
                        startActivity(mainClass);
                        finish();
                    } else {
                        Intent mainClass = new Intent(SplashActivity.this, Test.class);
                        mainClass.putExtra("data", result.getData());
                        startActivity(mainClass);
                        finish();
                    }


                }

            }.execute(request);

        }
    }
}