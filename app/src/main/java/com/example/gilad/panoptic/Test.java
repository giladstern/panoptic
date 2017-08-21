package com.example.gilad.panoptic;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.ListView;

import org.json.JSONException;

import java.util.List;


public class Test extends AppCompatActivity {

    List<Cluster> clusters = null;
    Filter filter = null;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        // Configure the search info and add any event listeners...

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (filter != null) filter.filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setLogo(R.drawable.ic_top_menu_logo);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

                    String data = getIntent().getStringExtra("data");
                    if (data == "") {
                        return;
                    }

                    try {
                        clusters = Cluster.parseClusters(data);
                    } catch (JSONException e) {
                        Log.d("Debug", e.getMessage());
                    }

                    ArticlesArrayAdapter adapter = new ArticlesArrayAdapter(Test.this, R.layout.list_row, clusters);
                    filter = adapter.getFilter();
                    ListView articlesListView = (ListView) findViewById(R.id.articles_list_view);

                    articlesListView.setAdapter(adapter);

    }
}
