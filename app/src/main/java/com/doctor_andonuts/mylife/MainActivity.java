package com.doctor_andonuts.mylife;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;

import com.doctor_andonuts.mylife.Chain.Chain;
import com.doctor_andonuts.mylife.Chain.ChainCreateFragment;
import com.doctor_andonuts.mylife.Chain.ChainDetailFragment;
import com.doctor_andonuts.mylife.Chain.ChainListFragment;
import com.doctor_andonuts.mylife.Chain.ChainManager;
import com.doctor_andonuts.mylife.Task.TaskListFragment;
import com.doctor_andonuts.mylife.Task.dummy.DummyContent;

import org.json.JSONObject;

import java.util.UUID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ChainListFragment.OnFragmentInteractionListener, TaskListFragment.OnListFragmentInteractionListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        if(BuildConfig.DEBUG) {
//            String chainOne = "{\"UUID\": \"0e36a204-9c40-465f-8dde-3b3ed0513acd\",\"Title\": \"Exercise\",\"StartDate\": \"2015-10-01\",\"EndDate\": \"2016-06-07\",\"Type\": \"MinMax\",\"MinDays\": 2,\"MaxDays\": \"4\",\"PerWeekValue\": null,\"Dates\": {\"2015-12-13\": \"D\",\"2015-12-14\": \"D\",\"2015-12-18\": \"S\",\"2015-12-21\": \"D\"}}";
//            String chainTwo = "{\"UUID\": \"23be9f99-4a24-45db-b892-650ba1772ed2\",\"Title\": \"Handstands\",\"StartDate\": \"2015-11-10\",\"EndDate\": null,\"Type\": \"PerWeek\",\"MinDays\": null,\"MaxDays\": null,\"PerWeekValue\": \"4\",\"Dates\": {\"2016-01-22\": \"D\",\"2016-01-23\": \"D\",\"2016-01-24\": \"D\"}}";
//            String fileName = "chain.data";
//
//            // TRY TO WRITE TEST DATA TO FILE
//            try {
//                FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
//                String writeString = chainOne + "\n" + chainTwo;
//                fileOutputStream.write(writeString.getBytes());
//                fileOutputStream.close();
//                Log.d("TestChainData", "Done Writing data to " + fileName);
//            } catch (Exception e) {
//                Log.d("TestChainData", "Problem writing data to : " + fileName + e.toString());
//            }
//        }



        // IF NO FRAGMENT SET, SET IT
        if (getFragmentManager().findFragmentById(R.id.content_area) == null) {
            loadChainListFragment();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    private void fromDetailToListFragment(ChainListFragment chainListFragment, ChainDetailFragment chainDetailFragment) {
        getFragmentManager().beginTransaction()
                .remove(chainDetailFragment)
                .add(R.id.content_area, chainListFragment, "ChainListFragment")
                .addToBackStack(null)
                .commit();

        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(R.string.app_name);
        setDrawerState(true);
        hideSoftKeyboard(findViewById(android.R.id.content));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
    }

    // Goes from Create to List Fragment
    private void fromCreateToListFragment(ChainListFragment chainListFragment, ChainCreateFragment chainCreateFragment) {
        getFragmentManager().beginTransaction()
                .remove(chainCreateFragment)
                .add(R.id.content_area, chainListFragment, "ChainListFragment")
                .addToBackStack(null)
                .commit();

        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(R.string.app_name);
        setDrawerState(true);
        hideSoftKeyboard(findViewById(android.R.id.content));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
    }


    private void loadChainListFragment() {
        ChainListFragment chainListFragment = new ChainListFragment();
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getFragmentManager().beginTransaction().replace(R.id.content_area, chainListFragment, "ChainListFragment").addToBackStack(null).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }

    private void loadTaskWarriorFragment() {
        TaskListFragment taskListFragment = new TaskListFragment();
        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getFragmentManager().beginTransaction().replace(R.id.content_area, taskListFragment, "TaskListFragment").addToBackStack(null).commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
    }

    // If you hit the drawer button on the ChainListFragment, show the drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            ChainListFragment chainListFragment = (ChainListFragment) getFragmentManager().findFragmentByTag("ChainListFragment");
            TaskListFragment taskListFragment = (TaskListFragment) getFragmentManager().findFragmentByTag("TaskListFragment");
            if (chainListFragment != null && chainListFragment.isVisible()) {
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            } else if (taskListFragment != null && taskListFragment.isVisible()) {
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
            loadChainListFragment();
        } else if (id == R.id.nav_taskwarrior) {
            loadTaskWarriorFragment();
        } else if (id == R.id.nav_settings) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @SuppressWarnings({"UnusedParameters", "unused"})
    public void addChain(View view) {
        Boolean submitIsAllowed = true;
        EditText title = (EditText) findViewById(R.id.editTitle);
        EditText startDate = (EditText) findViewById(R.id.startDate);
        EditText endDate = (EditText) findViewById(R.id.endDate);
        Spinner type = (Spinner) findViewById(R.id.typeSpinner);

        if(title.getText().toString().trim().equals("")) {
            title.setError("Title is required.");
            submitIsAllowed = false;
        }
        if(startDate.getText().toString().trim().equals("")) {
            startDate.setError("Start Date is required");
            submitIsAllowed = false;
        }
        if (type.getSelectedItem().equals("MinMax")) {
            EditText minDays = (EditText) findViewById(R.id.minDays);
            EditText maxDays = (EditText) findViewById(R.id.maxDays);
            if(maxDays.getText().toString().trim().equals("")) {
                maxDays.setError("Max Days is required");
                submitIsAllowed = false;
            }
            if(minDays.getText().toString().trim().equals("")) {
                minDays.setError("Min Days is required");
                submitIsAllowed = false;
            }
        } else {
            EditText perWeekValue = (EditText) findViewById(R.id.perWeekValue);
            if(perWeekValue.getText().toString().trim().equals("")) {
                perWeekValue.setError("Per Week Value is required");
                submitIsAllowed = false;
            }
        }

        if(submitIsAllowed) {

            JSONObject jsonChain = new JSONObject();
            String uuid = UUID.randomUUID().toString();
            try {
                jsonChain.put("UUID", uuid);
                jsonChain.put("Title", title.getText());
                jsonChain.put("StartDate", startDate.getText());
                if (endDate.getText().toString().equals("")) {
                    jsonChain.put("EndDate", JSONObject.NULL);
                } else {
                    jsonChain.put("EndDate", endDate.getText());
                }
                jsonChain.put("Type", type.getSelectedItem());
                if (type.getSelectedItem().equals("MinMax")) {
                    EditText minDays = (EditText) findViewById(R.id.minDays);
                    EditText maxDays = (EditText) findViewById(R.id.maxDays);
                    jsonChain.put("MinDays", minDays.getText());
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

            for (Chain chainLoop : chainManager.getChains()) {
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
    }

//    public void onFragmentInteraction(String id) {
//        Toast.makeText(getBaseContext(), "Clicked a list item (not needed?)", Toast.LENGTH_SHORT).show();
//    }

    private void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setDrawerState(boolean isEnabled) {
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

    // When a chain on the list fragment is clicked I think
    public void onFragmentInteraction(Chain chain) {
        setDrawerState(false);

        ChainDetailFragment chainDetailFragment = new ChainDetailFragment();
        chainDetailFragment.setChain(chain);
        ChainListFragment chainListFragment = (ChainListFragment) getFragmentManager().findFragmentByTag("ChainListFragment");
        getFragmentManager().beginTransaction()
                .remove(chainListFragment)
                .add(R.id.content_area, chainDetailFragment, "ChainDetailFragment")
                .addToBackStack(null)
                .commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
    }

    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
