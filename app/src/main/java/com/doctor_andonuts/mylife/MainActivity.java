package com.doctor_andonuts.mylife;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ChainListFragment.OnFragmentInteractionListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String chainOne = "{\"UUID\": \"0e36a204-9c40-465f-8dde-3b3ed0513acd\",\"Title\": \"Exercise\",\"StartDate\": \"2015-10-01\",\"EndDate\": \"2016-11-01\",\"Type\": \"MinMax\",\"MinDays\": 2,\"MaxDays\": \"4\",\"PerWeekValue\": null,\"Dates\": {\"2015-10-01\": \"D\",\"2015-10-02\": \"D\",\"2015-10-05\": \"S\",\"2015-10-10\": \"D\"}}";
//        String chainTwo = "{\"UUID\": \"23be9f99-4a24-45db-b892-650ba1772ed2\",\"Title\": \"Handstands\",\"StartDate\": \"2015-11-10\",\"EndDate\": null,\"Type\": \"PerWeek\",\"MinDays\": null,\"MaxDays\": null,\"PerWeekValue\": \"4\",\"Dates\": {\"2015-10-01\": \"D\",\"2015-10-02\": \"D\",\"2015-10-05\": \"S\",\"2015-10-10\": \"D\"}}";
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
//        try {
//            FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
//            String writeString = chainOne + "\n" + chainTwo;
//            fileOutputStream.write(writeString.getBytes());
//            fileOutputStream.close();
//            Log.d("TestChainData", "Done Writing data to " + fileName);
//        } catch (Exception e) {
//            Log.d("TestChainData", "Problem writing data to : " + fileName + e.toString());
//        }

        // TRY TO READ TEST DATA TO CONFIRM IT IS THERE
//        try {
//            File file = new File(getFilesDir(), fileName);
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
//            String line;
//
//            while ((line = bufferedReader.readLine()) != null) {
//                Log.d("TestChainData", line);
//            }
//        } catch(Exception e) {
//            Log.e("TestChainData", "Trying to read the data");
//        }

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


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        ChainCreateFragment chainCreateFragment  = (ChainCreateFragment) fragmentManager.findFragmentByTag("ChainCreateFragment");
        ChainListFragment chainListFragment = (ChainListFragment) getFragmentManager().findFragmentByTag("ChainListFragment");
        ChainDetailFragment chainDetailFragment= (ChainDetailFragment) getFragmentManager().findFragmentByTag("ChainDetailFragment");
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (chainDetailFragment != null && chainDetailFragment.isVisible()) {
            fromDetailToListFragment(chainListFragment, chainDetailFragment);
        } else if (chainCreateFragment != null && chainCreateFragment.isVisible()) {
            fromCreateToListFragment(chainListFragment, chainCreateFragment);
        } else {
            super.onBackPressed();
        }
    }

    // Goes from Details to List Fragment
    public void fromDetailToListFragment(ChainListFragment chainListFragment, ChainDetailFragment chainDetailFragment) {
        getFragmentManager().beginTransaction()
                .remove(chainDetailFragment)
                .add(R.id.content_area, chainListFragment, "ChainListFragment")
                .addToBackStack(null)
                .commit();

        assert getActionBar() != null;
        getSupportActionBar().setTitle(R.string.app_name);
        setDrawerState(true);
        hideSoftKeyboard(findViewById(android.R.id.content));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
    }

    // Goes from Create to List Fragment
    public void fromCreateToListFragment(ChainListFragment chainListFragment, ChainCreateFragment chainCreateFragment) {
        getFragmentManager().beginTransaction()
                .remove(chainCreateFragment)
                .add(R.id.content_area, chainListFragment, "ChainListFragment")
                .addToBackStack(null)
                .commit();

        assert getActionBar() != null;
        getSupportActionBar().setTitle(R.string.app_name);
        setDrawerState(true);
        hideSoftKeyboard(findViewById(android.R.id.content));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
    }



    // If you hit the drawer button on the ChainListFragment, show the drawer
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

    // What happens when you hit various items in the drawer
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


    public void addChain(View view) {
        // TODO: Validate that I have all the data I need
        JSONObject jsonChain = new JSONObject();

        String uuid = UUID.randomUUID().toString();
        try {
            jsonChain.put("UUID", uuid);
            EditText title = (EditText) findViewById(R.id.editTitle);
            jsonChain.put("Title", title.getText());
            EditText startDate = (EditText) findViewById(R.id.startDate);
            jsonChain.put("StartDate", startDate.getText());
            EditText endDate = (EditText) findViewById(R.id.endDate);
            if(endDate.getText().toString().equals("")) {
                jsonChain.put("EndDate", JSONObject.NULL);
            } else {
                jsonChain.put("EndDate", endDate.getText());
            }
            Spinner type = (Spinner) findViewById(R.id.typeSpinner);
            jsonChain.put("Type", type.getSelectedItem());
            if (type.getSelectedItem().equals("MinMax")) {
                EditText minDays = (EditText) findViewById(R.id.minDays);
                jsonChain.put("MinDays", minDays.getText());
                EditText maxDays = (EditText) findViewById(R.id.maxDays);
                jsonChain.put("MaxDays", maxDays.getText());
                jsonChain.put("PerWeekValue", JSONObject.NULL);
            } else {
                jsonChain.put("MinDays", JSONObject.NULL);
                jsonChain.put("MaxDays", JSONObject.NULL);
                EditText perWeekValue = (EditText) findViewById(R.id.perWeekValue);
                jsonChain.put("PerWeekValue", perWeekValue.getText());
            }

            jsonChain.put("Dates", new JSONObject());
        } catch (Exception e) {
            Log.d("MakingChains", "");
        }

        Log.d("jsonChain", jsonChain.toString());

        Chain chain = new Chain(jsonChain);
        ChainManager chainManager = new ChainManager(this);
        chainManager.addOrUpdateChain(chain);

        for(Chain chainLoop : chainManager.getChains()) {
            Log.d("ChainList", chainLoop.getJsonString());
        }


        ChainListFragment chainListFragment = (ChainListFragment) getFragmentManager().findFragmentByTag("ChainListFragment");
        ChainCreateFragment chainCreateFragment = (ChainCreateFragment) getFragmentManager().findFragmentByTag("ChainCreateFragment");
//        getFragmentManager().beginTransaction()
//                .remove(chainCreateFragment)
//                .add(R.id.content_area, chainListFragment, "ChainListFragment")
//                .commit();
        fromCreateToListFragment(chainListFragment, chainCreateFragment);

    }

//    public void onFragmentInteraction(String id) {
//        Toast.makeText(getBaseContext(), "Clicked a list item (not needed?)", Toast.LENGTH_SHORT).show();
//    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onFragmentInteraction(Chain chain) {
        setDrawerState(false);

        ChainDetailFragment chainDetailFragment = new ChainDetailFragment();
        chainDetailFragment.setChain(chain);
        // TODO: This is my problem spot.  I don't have the chain here so I can't pass it in.
        // TODO: I thought I could use onFragmentInteractions, but that happens in the ListFragment for a whole item being clicked on, not just the text like I have it
        // TODO: And I don't think I can call MainActivity functions from customArrayAdapter
        ChainListFragment chainListFragment = (ChainListFragment) getFragmentManager().findFragmentByTag("ChainListFragment");
        getFragmentManager().beginTransaction()
                .remove(chainListFragment)
                .add(R.id.content_area, chainDetailFragment, "ChainDetailFragment")
                .addToBackStack(null)
                .commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
    }
}
