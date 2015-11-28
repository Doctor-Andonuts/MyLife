package com.andonuts.mylife;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        TODO:  Do these things
            5. Figure out steps to display file data in the ListFragment
         */
        String chainOne = "{\"UUID\": \"0e36a204-9c40-465f-8dde-3b3ed0513acd\",\"Title\": \"Exercise\",\"StartDate\": \"2015-10-01\",\"EndDate\": \"2016-11-01\",\"Type\": \"MinMax\",\"MinDays\": 2,\"MaxDays\": \"4\",\"PerWeekValue\": null,\"Dates\": {\"2015-10-01\": \"D\",\"2015-10-02\": \"D\",\"2015-10-05\": \"S\",\"2015-10-10\": \"D\"}}";
        String chainTwo = "{\"UUID\": \"23be9f99-4a24-45db-b892-650ba1772ed2\",\"Title\": \"Handstands\",\"StartDate\": \"2015-11-10\",\"EndDate\": null,\"Type\": \"PerWeek\",\"MinDays\": null,\"MaxDays\": null,\"PerWeekValue\": \"4\",\"Dates\": {\"2015-10-01\": \"D\",\"2015-10-02\": \"D\",\"2015-10-05\": \"S\",\"2015-10-10\": \"D\"}}";
        String fileName = "chain.data";

        /*

        UUID
        Title
        StartDate
        EndDate
        Type
        MinDays
        MaxDays
        PerWeekValue
        Dates

         */

        // TRY TO WRITE TEST DATA TO FILE
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            String writeString = chainOne + "\n" + chainTwo;
            fileOutputStream.write(writeString.getBytes());
            fileOutputStream.close();
            Log.d("TestChainData", "Done Writing data to " + fileName);
        } catch (Exception e) {
            Log.d("TestChainData", "Problem writing data to : " + fileName + e.toString());
        }

        // TRY TO READ TEST DATA TO CONFIRM IT IS THERE
        try {
            File file = new File(getFilesDir(), fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                Log.d("TestChainData", line);
            }
        } catch(Exception e) {
            Log.e("TestChainData", "Trying to read the data");
        }

        // IF NO FRAGMENT SET, SET IT
        if (getFragmentManager().findFragmentById(R.id.content_area) == null) {
            ChainListFragment chainListFragment = new ChainListFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.content_area, chainListFragment, "ChainListFragment")
                    .commit();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                setDrawerState(false);

                ChainCreateFragment chainCreateFragment = new ChainCreateFragment();
                ChainListFragment chainListFragment = (ChainListFragment) getFragmentManager().findFragmentByTag("ChainListFragment");
                getFragmentManager().beginTransaction()
                        .remove(chainListFragment)
                        .add(R.id.content_area, chainCreateFragment, "ChainCreateFragment")
                        .addToBackStack(null)
                        .commit();
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.hide();
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
    }

    public void setDrawerState(boolean isEnabled) {
        if ( isEnabled ) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//            actionBarDrawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            actionBarDrawerToggle.syncState();

        }
        else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//            actionBarDrawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            actionBarDrawerToggle.syncState();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        ChainCreateFragment chainCreateFragment  = (ChainCreateFragment) fragmentManager.findFragmentByTag("ChainCreateFragment");
        ChainListFragment chainListFragment = (ChainListFragment) getFragmentManager().findFragmentByTag("ChainListFragment");

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (chainCreateFragment != null && chainCreateFragment.isVisible()) {
            backToListFragment(chainListFragment, chainCreateFragment);
            setDrawerState(true);
        } else {
            super.onBackPressed();
        }
    }

    public void backToListFragment(ChainListFragment chainListFragment, ChainCreateFragment chainCreateFragment) {
        getFragmentManager().beginTransaction()
                .remove(chainCreateFragment)
                .add(R.id.content_area, chainListFragment, "ChainListFragment")
                .addToBackStack(null)
                .commit();

        assert getActionBar() != null;
        getSupportActionBar().setTitle(R.string.app_name);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            ChainListFragment chainListFragment = (ChainListFragment) getFragmentManager().findFragmentByTag("ChainListFragment");
            if (chainListFragment != null && chainListFragment.isVisible()) {
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chain) {

        } else if (id == R.id.nav_taskwarrior) {

        } else if (id == R.id.nav_settings) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


//    public void onFragmentInteraction(String id) {
//        Toast.makeText(getBaseContext(), "Clicked a list item (not needed?)", Toast.LENGTH_SHORT).show();
//    }
}
