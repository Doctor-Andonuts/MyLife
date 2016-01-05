package com.doctor_andonuts.mylife;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


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
        loadData();
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


    public void loadData() {
        LinearLayout calendarGroup = (LinearLayout) getActivity().findViewById(R.id.calendarGroup);
        LinearLayout weekGroup[] = new LinearLayout[4];

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentMonthOfYearFormat = new SimpleDateFormat("M", Locale.US);
        int currentMonthOfYear = Integer.parseInt(currentMonthOfYearFormat.format(calendar.getTime()));
        SimpleDateFormat currentDayInMonthFormat = new SimpleDateFormat("d", Locale.US);
        int currentDayInMonth = Integer.parseInt(currentDayInMonthFormat.format(calendar.getTime()));

        int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int daysToGoBack = 21 + dayInWeek - 1;
        calendar.add(Calendar.DATE, -daysToGoBack);

        for (int w = 0; w < 4; w++) {
            weekGroup[w] = new LinearLayout(getActivity());
            calendarGroup.addView(weekGroup[w]);

            Button buttons[] = new Button[7];
            for (int d = 0; d < 7; d++) {
                buttons[d] = new Button(getActivity());

                SimpleDateFormat targetDayInMonthFormat = new SimpleDateFormat("d", Locale.US);
                SimpleDateFormat targetMonthOfYearFormat = new SimpleDateFormat("M", Locale.US);
                int targetDayInMonth = Integer.parseInt(targetDayInMonthFormat.format(calendar.getTime()));
                int targetMonthOfYear = Integer.parseInt(targetMonthOfYearFormat.format(calendar.getTime()));

                SimpleDateFormat chainDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String chainDateTest = chainDateFormat.format(calendar.getTime());

                GradientDrawable dayDrawable = new GradientDrawable();
                dayDrawable.setShape(GradientDrawable.RECTANGLE);
                if(targetMonthOfYear == currentMonthOfYear && targetDayInMonth == currentDayInMonth) {
                    dayDrawable.setStroke(5, Color.BLACK);
                } else {
                    dayDrawable.setStroke(1, Color.BLACK);
                }


                if(chain.getDayStatus(chainDateTest).equals("Done")) {
                    dayDrawable.setColor(0xFF43a047);
                } else if(chain.getDayStatus(chainDateTest).equals("Should do")) {
                    dayDrawable.setColor(0xFFfdd835);
                } else if(chain.getDayStatus(chainDateTest).equals("No need")) {
                    dayDrawable.setColor(0xFF1b5e20);
                } else if(targetMonthOfYear == currentMonthOfYear && targetDayInMonth > currentDayInMonth) {
                    dayDrawable.setColor(0xFF666666);
                } else if(chain.getDayStatus(chainDateTest).equals("DO IT!")) {
                    dayDrawable.setColor(0xFFc62828);
                } else {
                    dayDrawable.setColor(0xFFFFFFFF);
                }


                buttons[d].setBackground(dayDrawable);
                buttons[d].setText(String.valueOf(targetDayInMonth));
                calendar.add(Calendar.DATE, 1);

                weekGroup[w].addView(buttons[d]);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, RelativeLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                float scale = getResources().getDisplayMetrics().density;
                int dpAsPixels = (int) (5*scale + 0.5f); // sizeInDP * scale / 0.5f
                p.setMargins(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
                buttons[d].setLayoutParams(p);
            }
        }

        TextView chainTitleTextView = (TextView) getActivity().findViewById(R.id.chainTitle);
        chainTitleTextView.setText(chain.getTitle());

        TextView chainJsonTextView = (TextView) getActivity().findViewById(R.id.chainJson);
        chainJsonTextView.setText(chain.getJsonString());
    }

}
