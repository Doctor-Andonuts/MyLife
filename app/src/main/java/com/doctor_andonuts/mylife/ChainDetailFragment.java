package com.doctor_andonuts.mylife;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


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
        calendarGroup.removeAllViews();
        LinearLayout weekGroup[] = new LinearLayout[4];

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentMonthOfYearFormat = new SimpleDateFormat("M", Locale.US);
        int currentMonthOfYear = Integer.parseInt(currentMonthOfYearFormat.format(calendar.getTime()));
        SimpleDateFormat currentDayInMonthFormat = new SimpleDateFormat("d", Locale.US);
        int currentDayInMonth = Integer.parseInt(currentDayInMonthFormat.format(calendar.getTime()));

        int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int daysToGoBack = 21 + dayInWeek - 2;
        calendar.add(Calendar.DATE, -daysToGoBack);

        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startDate = Calendar.getInstance();
        try {
            startDate.setTime(myDateFormat.parse(chain.getStartDate()));
        } catch (Exception e) {}

        Button buttonLabels[] = new Button[7];
        String weekLabel[] = new String[7];
        weekLabel[0] = "M";
        weekLabel[1] = "T";
        weekLabel[2] = "W";
        weekLabel[3] = "T";
        weekLabel[4] = "F";
        weekLabel[5] = "S";
        weekLabel[6] = "S";
        LinearLayout calendarLabelGroup = (LinearLayout) getActivity().findViewById(R.id.calendarLabelGroup);
        calendarLabelGroup.removeAllViews();
        for (int d = 0; d < 7; d++) {
            buttonLabels[d] = new Button(getActivity());

            GradientDrawable dayDrawable = new GradientDrawable();
            dayDrawable.setShape(GradientDrawable.RECTANGLE);
            dayDrawable.setCornerRadius(7);
            buttonLabels[d].setBackground(dayDrawable);
            buttonLabels[d].setText(weekLabel[d]);
            dayDrawable.setColor(0xFFFFFFFF);
            buttonLabels[d].setPadding(0,0,0,0);


            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(0, 50, 1.0f);
            float scale = getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (5*scale + 0.5f); // sizeInDP * scale / 0.5f
            p.setMargins(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
            buttonLabels[d].setLayoutParams(p);

            calendarLabelGroup.addView(buttonLabels[d]);
        }

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
                dayDrawable.setCornerRadius(7);

                if(targetMonthOfYear == currentMonthOfYear && targetDayInMonth == currentDayInMonth) {
                    dayDrawable.setStroke(10, Color.LTGRAY);
                } else {
                    dayDrawable.setStroke(1, Color.BLACK);
                }

                buttons[d].setOnClickListener(new DetailDoneButtonOnClickListener(chainDateFormat.format(calendar.getTime())));
                buttons[d].setBackground(dayDrawable);
                buttons[d].setText(String.valueOf(targetDayInMonth));

                if(calendar.before(startDate)) {
                    dayDrawable.setColor(0xFFCCCCCC);
                    dayDrawable.setStroke(1, Color.WHITE);
                    buttons[d].setTextColor(0xFFFFFFFF);
                    buttons[d].setOnClickListener(null);
                } else if(chain.getDayStatus(chainDateTest).equals("Done")) {
                    dayDrawable.setColor(0xFF43a047);
                } else if(chain.getDayStatus(chainDateTest).equals("Should do")) {
                    dayDrawable.setColor(0xFFfdd835);
                } else if(chain.getDayStatus(chainDateTest).equals("No need")) {
                    dayDrawable.setColor(0xFF1b5e20);
                } else if(targetMonthOfYear == currentMonthOfYear && targetDayInMonth > currentDayInMonth) {
                    dayDrawable.setColor(0xFFCCCCCC);
                    dayDrawable.setStroke(1, Color.WHITE);
                    buttons[d].setTextColor(0xFFFFFFFF);
                    buttons[d].setOnClickListener(null);
                } else if(chain.getDayStatus(chainDateTest).equals("DO IT!")) {
                    dayDrawable.setColor(0xFFc62828);
                } else {
                    dayDrawable.setColor(0xFFFFFFFF);
                }

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
        chainTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDescription();
            }
        });

        TextView chainJsonTextView = (TextView) getActivity().findViewById(R.id.chainJson);
        chainJsonTextView.setText(chain.getJsonString());

        TextView chainEndDateTextView = (TextView) getActivity().findViewById(R.id.chainEndDate);
        chainEndDateTextView.setText(chain.getEndDate());
        chainEndDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragmentEndDate();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

    }


    private void editDescription() {
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final TextView chainTitleTextView = (TextView) getActivity().findViewById(R.id.chainTitle);
        builder.setTitle("Edit Title");
        builder.setCancelable(true);
        final EditText titleInput = new EditText(getActivity());
        titleInput.setText(chainTitleTextView.getText());
        builder.setView(titleInput);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                chain.setTitle(titleInput.getText().toString());
                chainTitleTextView.setText(titleInput.getText());

                ChainManager chainManager = new ChainManager(getActivity());
                chainManager.addOrUpdateChain(chain);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public class DetailDoneButtonOnClickListener implements View.OnClickListener
    {

        String chainDate;
        public DetailDoneButtonOnClickListener(String chainDate) {
            this.chainDate = chainDate;
        }

        @Override
        public void onClick(View v)
        {
            if(chain.getDateValue(chainDate).equals("D"))
            {
                chain.setDone(chainDate, "REMOVE");
            } else {
                chain.setDone(chainDate, "Done");
            }
            ChainManager chainManager = new ChainManager(getActivity());
            chainManager.addOrUpdateChain(chain);
            loadData();
        }

    };

    public static class DatePickerFragmentEndDate extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            TextView endDate = (TextView) getActivity().findViewById(R.id.chainEndDate);
            int correctedMonth = month + 1; // TODO: Need leading zeros here
            String dateText = year + "-" + correctedMonth + "-" + day;
            endDate.setText(dateText);
            // TODO: This needs to save the change, but I don't have the chain here
        }
    }
}
