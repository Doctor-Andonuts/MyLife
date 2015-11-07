package com.andonuts.mylife;

import android.content.Context;
import android.net.Uri;
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
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ChainListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        TODO:  Do these things
            1. Create Strings for chain data
            2. Open the chain file
            3. Save my string data to file overwriting all data inside
            4. Output file data to Log to confirm it worked
            5. Figure out steps to display file data in the ListFragment
         */
        String chainOne = "{\"Title\": \"Exercise\",\"StartDate\": \"2015-10-01\",\"EndDate\": \"2015-11-01\",\"Type\": \"MinMax\",\"MinDays\": 2,\"MaxDays\": \"4\",\"PerWeekValue\": null,\"Dates\": {\"2015-10-01\": \"D\",\"2015-10-02\": \"D\",\"2015-10-05\": \"S\",\"2015-10-10\": \"D\"}}";
        String chainTwo = "{\"Title\": \"Handstands\",\"StartDate\": \"2015-10-01\",\"EndDate\": \"null\",\"Type\": \"PerWeek\",\"MinDays\": null,\"MaxDays\": null,\"PerWeekValue\": \"4\",\"Dates\": {\"2015-10-01\": \"D\",\"2015-10-02\": \"D\",\"2015-10-05\": \"S\",\"2015-10-10\": \"D\"}}";
        String fileName = "chain.data";

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


        if (getFragmentManager().findFragmentById(R.id.content_area) == null) {
            ChainListFragment chainListFragment = new ChainListFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.content_area, chainListFragment, "ArrayListFrag")
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
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            return true;
//        }

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onFragmentInteraction(String id) {
        Toast.makeText(getBaseContext(), "Clicked a thing", Toast.LENGTH_SHORT).show();
    }
}
