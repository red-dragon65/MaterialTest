package com.cyberllama.mujtabaashfaq.materialtest;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Save which fragment was opened.
    private SharedPreferences savedVals;

    //Holds frag id.
    int id;

    //Hold last list view item position.
    int lastListView;

    //TODO: Highlight last selected list view item on app start.

    //Gen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //activityMainSet();
        customMainSet();

    }


    //Creates default nav drawer.
    public void activityMainSet(){
        setContentView(R.layout.activity_main);

        //Set custom app bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Custom circle button.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Get/set saved frag.
        savedVals = getSharedPreferences("SavedVals", MODE_PRIVATE);
        id = savedVals.getInt("fragId", 0);
        getFrag(id);
    }

    //Creates custom nav drawer.
    public void customMainSet(){
        setContentView(R.layout.custom_main);

        //Set custom app bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create nav drawer.
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //Get/set saved frag.
        savedVals = getSharedPreferences("SavedVals", 0);
        id = savedVals.getInt("fragId", 0);
        lastListView = savedVals.getInt("viewPos", 0);
        customFrag(id, drawer);

        //Handle nav drawer.
        String[] data = {"Import", "Gallery'", "N/A"};
        ListAdapter dapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, data);
        final ListView characterListView = (ListView) findViewById(R.id.left_drawer);
        characterListView.setAdapter(dapter);

        //Nav list listener.
        characterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                customFrag((int) id, drawer);

                //Revert last selected item.
                if (lastListView >= 0) {
                    characterListView.getChildAt(lastListView).setBackgroundColor(Color.TRANSPARENT);
                }

                //Update selected list view.
                //view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                view.setBackgroundColor(Color.parseColor("#ffbdbdbd"));

                //Update last list view item.
                lastListView = position;
            }
        });
    }

    //Gen
    protected void onPause(){

        //Save last frag opened.
        //Save last list view.
        SharedPreferences.Editor editor = savedVals.edit();
        editor.putInt("fragId", id);
        editor.putInt("viewPos", lastListView);
        editor.apply();

        super.onPause();
    }

    //Gen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Gen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Handle nav auto close (both activity and custom).
    @Override
    public void onBackPressed() {
        //Close navigation drawer onBackPressed.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    //Handle activity_main nav selection.
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        id = item.getItemId();

        //set frag.
        getFrag(id);

        //Close drawer after navigation item is selected.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Set fragment for activity_main
    public void getFrag(int frag){

        if (frag == R.id.nav_camara) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction().replace(R.id.content, new TestFragment()).commit();
            getSupportActionBar().setTitle("Import");
        } else if (frag == R.id.nav_gallery) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, new AnotherFragment()).commit();
            getSupportActionBar().setTitle("Gallery");
        } else if (frag == R.id.nav_slideshow) {

        } else if (frag == R.id.nav_manage) {

        } else if (frag == R.id.nav_share) {

        } else if (frag == R.id.nav_send) {

        }

    }



    //Handle custom_main nav selection.
    //Set fragment for custom_main
    //Handle custom_main nav auto-close.
    public void customFrag(int frag, DrawerLayout drawer){

        id = frag;

        if (frag == 0) {
            // Handle the camera action
            getSupportFragmentManager().beginTransaction().replace(R.id.content, new TestFragment()).commit();
            getSupportActionBar().setTitle("Import");
            drawer.closeDrawer(GravityCompat.START);
        } else if (frag == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content, new AnotherFragment()).commit();
            getSupportActionBar().setTitle("Gallery");
            drawer.closeDrawer(GravityCompat.START);
        } else if (frag == R.id.nav_slideshow) {

        } else if (frag == R.id.nav_manage) {

        } else if (frag == R.id.nav_share) {

        } else if (frag == R.id.nav_send) {

        }
    }
}
