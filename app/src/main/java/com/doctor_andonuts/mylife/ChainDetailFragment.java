package com.doctor_andonuts.mylife;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ChainDetailFragment extends Fragment {
    private Chain chain;

    public ChainDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*


            Title - Could change

            StartDate - should be okay
            EndDate - should be okay

            Type - nope but display
            MinDays - messes up historical
            MaxDays - messes up historical
            PerWeekValue - messes up historical
        */


        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity.getSupportActionBar() != null;
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Chain Detail");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView chainTitleTextView = (TextView) getActivity().findViewById(R.id.chainTitle);
        chainTitleTextView.setText(chain.getTitle());

        TextView chainJsonTextView = (TextView) getActivity().findViewById(R.id.chainJson);
        chainJsonTextView.setText(chain.getJsonString());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chain_detail, container, false);
    }

    public void setChain(Chain chain) {
        this.chain = chain;
    }




}
