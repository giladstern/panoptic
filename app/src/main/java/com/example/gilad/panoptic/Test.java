package com.example.gilad.panoptic;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

class NavItem {
    String mTitle;

    public NavItem(String title) {
        mTitle = title;
    }
}

class DrawerListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<NavItem> mNavItems;

    public DrawerListAdapter(Context context, ArrayList<NavItem> navItems) {
        mContext = context;
        mNavItems = navItems;
    }

    @Override
    public int getCount() {
        return mNavItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mNavItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawer_item, null);
        }
        else {
            view = convertView;
        }

        TextView titleView = (TextView) view.findViewById(R.id.title);
        //TextView subtitleView = (TextView) view.findViewById(R.id.subTitle);
        //ImageView iconView = (ImageView) view.findViewById(R.id.icon);

        titleView.setText( mNavItems.get(position).mTitle );
        //subtitleView.setText( mNavItems.get(position).mSubtitle );
        //iconView.setImageResource(mNavItems.get(position).mIcon);

        return view;
    }
}

public class Test extends AppCompatActivity {

    List<Cluster> clusters = null;
    Filter filter = null;
    private RelativeLayout linearLayout;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        linearLayout = (RelativeLayout) findViewById(R.id.drawerPane);

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

    ListView mDrawerList;
    RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle
        // If it returns true, then it has handled
        // the nav drawer indicator touch event
        if (mDrawerToggle == null) {return false;}
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle == null) {return;}
        mDrawerToggle.syncState();
    }

    /*
* Called when a particular item from the navigation drawer
* is selected.
* */
    private void selectItemFromDrawer(int position) {

        Log.d("MYINT", "value: " + position);

//        Toast.makeText(this, position,
//                Toast.LENGTH_LONG).show();

        //Fragment fragment = new PreferencesFragment();

        //FragmentManager fragmentManager = getFragmentManager();
        //fragmentManager.beginTransaction()
        //        .replace(R.id.mainContent, fragment)
        //        .commit();

        //mDrawerList.setItemChecked(position, true);
        //setTitle(mNavItems.get(position).mTitle);

        // Close the drawer
        //mDrawerLayout.closeDrawer(mDrawerPane);
    }

    // Called when invalidateOptionsMenu() is invoked
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(linearLayout);
        menu.findItem(R.id.action_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myToolbar.setLogo(R.drawable.ic_top_menu_logo);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mNavItems.add(new NavItem("Aljazeera"));
        mNavItems.add(new NavItem("The New York Times"));
        mNavItems.add(new NavItem("WSJ"));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawerListAdapter dadapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setAdapter(dadapter);



        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d("TAG", "onDrawerClosed: " + getTitle());

                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });

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
