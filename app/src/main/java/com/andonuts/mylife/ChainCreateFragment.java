package com.andonuts.mylife;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.UUID;


public class ChainCreateFragment extends Fragment {

    public ChainCreateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        assert activity.getSupportActionBar() != null;
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Create New Task");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        EditText startDate = (EditText) getActivity().findViewById(R.id.startDate);
        startDate.setInputType(InputType.TYPE_NULL);
        startDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Show your calender here
                    DialogFragment newFragment = new DatePickerFragmentStartDate();
                    newFragment.show(getFragmentManager(), "datePicker");
                } else {
                    //Hide your calender here
                }
            }
        });

        EditText endDate = (EditText) getActivity().findViewById(R.id.endDate);
        endDate.setInputType(InputType.TYPE_NULL);
        endDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //Show your calender here
                    DialogFragment newFragment = new DatePickerFragmentEndDate();
                    newFragment.show(getFragmentManager(), "datePicker");
                } else {
                    //Hide your calender here
                }
            }
        });

        Spinner spinner = (Spinner) getActivity().findViewById(R.id.typeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // TODO: Code Here
                if (position == 1) {
                    LinearLayout spinnerContent = (LinearLayout) getActivity().findViewById(R.id.spinnerContent);
                    spinnerContent.removeAllViews();

                    EditText perWeekValue = new EditText(getActivity());
                    perWeekValue.setHint("Per Week Value");
                    perWeekValue.setId(R.id.perWeekValue);
                    spinnerContent.addView(perWeekValue);
                } else {
                    LinearLayout spinnerContent = (LinearLayout) getActivity().findViewById(R.id.spinnerContent);
                    spinnerContent.removeAllViews();

                    EditText maxDays = new EditText(getActivity());
                    maxDays.setHint("Max Days");
                    maxDays.setInputType(InputType.TYPE_CLASS_NUMBER);
                    maxDays.setId(R.id.maxDays);
                    spinnerContent.addView(maxDays);

                    EditText minDays = new EditText(getActivity());
                    minDays.setHint("Min Days");
                    minDays.setInputType(InputType.TYPE_CLASS_NUMBER);
                    minDays.setId(R.id.minDays);
                    spinnerContent.addView(minDays);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chain_create, container, false);
    }



    public static class DatePickerFragmentStartDate extends DialogFragment
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
            EditText startDate = (EditText) getActivity().findViewById(R.id.startDate);
            int correctedMonth = month + 1;
            String dateText = year + "-" + correctedMonth + "-" + day;
            startDate.setText(dateText);
        }
    }

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
            EditText endDate = (EditText) getActivity().findViewById(R.id.endDate);
            int correctedMonth = month + 1;
            String dateText = year + "-" + correctedMonth + "-" + day;
            endDate.setText(dateText);
        }
    }
}
