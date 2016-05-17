package com.doctor_andonuts.mylife.Chain;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doctor_andonuts.mylife.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class ChainDetailFragment extends Fragment {
    private Chain chain;
    private final String TAG = "ChainDetailFragment";

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.menu_chain_details, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        } else if (id == R.id.action_delete) {
            ChainManager chainManager = new ChainManager(getActivity());
            chainManager.removeChain(chain);

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


    private void loadData() {
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

        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar startDate = Calendar.getInstance();
        try {
            startDate.setTime(myDateFormat.parse(chain.getStartDate()));
        } catch (Exception e) {
            Log.e(TAG, "Could not set time correctly");
        }
        Calendar endDate = Calendar.getInstance();
        try {
            endDate.setTime(myDateFormat.parse(chain.getEndDate()));
            endDate.add(Calendar.HOUR, 23);
            endDate.add(Calendar.MINUTE, 59);
            endDate.add(Calendar.SECOND, 59);
        } catch (Exception e) {
            Log.e(TAG, "Could not set time correctly");
        }

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

                SimpleDateFormat chainDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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

                int buttonTextColor = Color.BLACK;

                if(calendar.before(startDate)) {
                    dayDrawable.setColor(0xFFCCCCCC);
                    dayDrawable.setStroke(1, Color.WHITE);
                    buttons[d].setTextColor(Color.WHITE);
                    buttons[d].setOnClickListener(null);
                } else if(calendar.after(endDate)) {
                    dayDrawable.setColor(0xFFCCCCCC);
                    dayDrawable.setStroke(1, Color.WHITE);
                    buttons[d].setTextColor(Color.WHITE);
                    buttons[d].setOnClickListener(null);
                } else if(chain.getDayStatus(chainDateTest).equals("Done")) {
                    buttons[d].setTextColor(buttonTextColor);
                    dayDrawable.setColor(getResources().getColor(R.color.done));
                } else if(chain.getDayStatus(chainDateTest).equals("Should do")) {
                    buttons[d].setTextColor(buttonTextColor);
                    dayDrawable.setColor(getResources().getColor(R.color.shouldDo));
                } else if(chain.getDayStatus(chainDateTest).equals("No need")) {
                    buttons[d].setTextColor(buttonTextColor);
                    dayDrawable.setColor(getResources().getColor(R.color.noNeed));
                } else if(targetMonthOfYear == currentMonthOfYear && targetDayInMonth > currentDayInMonth) {
                    dayDrawable.setColor(0xFFCCCCCC);
                    dayDrawable.setStroke(1, Color.WHITE);
                    buttons[d].setTextColor(Color.WHITE);
                    buttons[d].setOnClickListener(null);
                } else if(chain.getDayStatus(chainDateTest).equals("DO IT!")) {
                    buttons[d].setTextColor(buttonTextColor);
                    dayDrawable.setColor(getResources().getColor(R.color.doIt));
                } else {
                    dayDrawable.setColor(Color.WHITE);
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

        TextView chainStartDateLabelTextView = (TextView) getActivity().findViewById(R.id.chainStartDateLabel);
        chainStartDateLabelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStartDate();
            }
        });
        TextView chainStartDateTextView = (TextView) getActivity().findViewById(R.id.chainStartDate);
        chainStartDateTextView.setText(chain.getStartDate());
        chainStartDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStartDate();
            }
        });

        TextView chainEndDateLabelTextView = (TextView) getActivity().findViewById(R.id.chainEndDateLabel);
        chainEndDateLabelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEndDate();
            }
        });
        TextView chainEndDateTextView = (TextView) getActivity().findViewById(R.id.chainEndDate);
        chainEndDateTextView.setText(chain.getEndDate());
        chainEndDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEndDate();
            }
        });

        TextView chainTypeTextView = (TextView) getActivity().findViewById(R.id.chainType);
        chainTypeTextView.setText(chain.getType());

        TextView chainUUIDView = (TextView) getActivity().findViewById(R.id.chainUUID);
        chainUUIDView.setText(chain.getUUID());

        TextView chainOnceOverTextView = (TextView) getActivity().findViewById(R.id.chainOnceOver);
        Calendar today = Calendar.getInstance();
        String chainDateTest = myDateFormat.format(today.getTime());
        chainOnceOverTextView.setText(chain.getOnceOverString(chainDateTest));

        TextView chainTypeDataTextView = (TextView) getActivity().findViewById(R.id.chainTypeData);
        if(chain.getType().equals("MinMax")) {
            String text = chain.getMinDays() + " - " + chain.getMaxDays();
            chainTypeDataTextView.setText(text);
        } else {
            String text = Integer.toString(chain.getPerWeekValue());
            chainTypeDataTextView.setText(text);
        }
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

        final String chainDate;
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

    }

    private void editEndDate() {
        Calendar endDate = Calendar.getInstance();
        if(!chain.getEndDate().equals("null")) {
            SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            try {
                endDate.setTime(myDateFormat.parse(chain.getEndDate()));
            } catch (Exception e) {
                Log.e(TAG, "Could not set time correctly");
            }
        }

        int mYear = endDate.get(Calendar.YEAR);
        int mMonth = endDate.get(Calendar.MONTH);
        int mDay = endDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                TextView endDate = (TextView) getActivity().findViewById(R.id.chainEndDate);
                int correctedMonth = monthOfYear + 1;
                String dateText = String.format ("%04d", year) + "-" + String.format ("%02d", correctedMonth) + "-" + String.format ("%02d", dayOfMonth);
                endDate.setText(dateText);

                chain.setEndDate(dateText);
                ChainManager chainManager = new ChainManager(getActivity());
                chainManager.addOrUpdateChain(chain);
                loadData();
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Remove", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    TextView endDate = (TextView) getActivity().findViewById(R.id.chainEndDate);
                    endDate.setText("");

                    chain.setEndDate("null");
                    ChainManager chainManager = new ChainManager(getActivity());
                    chainManager.addOrUpdateChain(chain);
                    loadData();
                }
            }
        });
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Set", datePickerDialog);
        datePickerDialog.show();
    }

    private void editStartDate() {
        Calendar startDate = Calendar.getInstance();
        if(!chain.getStartDate().equals("null")) {
            SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            try {
                startDate.setTime(myDateFormat.parse(chain.getStartDate()));
            } catch (Exception e) {
                Log.e(TAG, "Could not set time correctly");
            }
        }

        int mYear = startDate.get(Calendar.YEAR);
        int mMonth = startDate.get(Calendar.MONTH);
        int mDay = startDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                TextView startDate = (TextView) getActivity().findViewById(R.id.chainStartDate);
                int correctedMonth = monthOfYear + 1;
                String dateText = String.format ("%04d", year) + "-" + String.format ("%02d", correctedMonth) + "-" + String.format ("%02d", dayOfMonth);
                startDate.setText(dateText);

                chain.setStartDate(dateText);
                ChainManager chainManager = new ChainManager(getActivity());
                chainManager.addOrUpdateChain(chain);
                loadData();
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "", datePickerDialog);
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Set", datePickerDialog);
        datePickerDialog.show();
    }

}
