package com.doctor_andonuts.mylife;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Andonuts on 11/7/2015.
 */
public class CustomArrayAdapter extends ArrayAdapter<Chain> {
    private final Context context;
    private final List<Chain> chains;
    private final ChainListFragment.OnFragmentInteractionListener mListener;

    @Override
    public void notifyDataSetChanged() {
        setNotifyOnChange(false);

        ChainManager chainManager = new ChainManager(context);
        chains.clear();
        chains.addAll(chainManager.getChains());
        ChainComparator chainComparator = new ChainComparator();
        sort(chainComparator);

        super.notifyDataSetChanged();
    }

    public CustomArrayAdapter(Context context, List<Chain> chains, ChainListFragment.OnFragmentInteractionListener mListener) {
        super(context, -1, chains);
        this.context = context;
        this.chains = chains;
        this.mListener = mListener;

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.chain_list_item, parent, false);

        Chain chain = chains.get(position);

        TextView descriptionTextView = (TextView) rowView.findViewById(R.id.description);
        descriptionTextView.setText(chain.getTitle());
        descriptionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chain chain = chains.get(position);
                mListener.onFragmentInteraction(chain);
            }
        });



        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar today = Calendar.getInstance();
        final String todayString = myDateFormat.format(today.getTime());
        Log.d("DateCheck", todayString);




// -------------------------------------

        String startDateString = chain.getStartDate();
        String endDateString = chain.getEndDate();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        try {
            startDate.setTime(myDateFormat.parse(startDateString));
            if(endDateString == null || endDateString.equals("null")) {
                endDate = null;
            } else {
                endDate.setTime(myDateFormat.parse(endDateString));
            }
            today.setTime(myDateFormat.parse(myDateFormat.format(today.getTime())));
        } catch (Exception e) {
            Log.e("Time Crap", "END DATE STRING: " + endDateString);
            Log.e("Time Crap", "JSON: " + chain.getJsonString());
            Log.e("Time Crap", "Parse Error: " + e.toString());
        }

        if(startDate.after(today)) {
            rowView.setBackgroundColor(0xFF999999);
        }
        if(!chain.getEndDate().equals("null") && endDate.before(today)) {
            rowView.setBackgroundColor(0xFF999999);
        }


// -------------------------------------



        TextView onceOverTextView= (TextView) rowView.findViewById(R.id.onceOver);
        onceOverTextView.setText(chain.getOnceOverString(todayString));

        Button doneButton = (Button) rowView.findViewById(R.id.listButton_Done);
        doneButton.setText(String.valueOf(chain.getCurrentLength(todayString)));
        doneButton.setTextColor(0xFFFFFFFF);


        if(chain.getType().equals("MinMax")) {
            String dayStatus = chain.getDayStatus(todayString);
            if (dayStatus.equals("Done")) {
                doneButton.setBackgroundColor(0xFF43a047); // Light Green
            } else if (dayStatus.equals("Should do")) {
                doneButton.setBackgroundColor(0xFFfdd835); // Yellow
            } else if (dayStatus.equals("No need")) {
                doneButton.setBackgroundColor(0xFF1b5e20); // Dark Green
            } else if (dayStatus.equals("DO IT!")) {
                doneButton.setBackgroundColor(0xFFc62828); // Red
            } else {
                doneButton.setBackgroundColor(0xFF666666);
            }
        } else {
            int[] onceOverData = chain.getOnceOverData(todayString);
            if(onceOverData[0] == onceOverData[1]) {
                doneButton.setBackgroundColor(0xFFc62828); // Red
            } else if((double)onceOverData[0] / (double)onceOverData[1] >= 0.5) {
                doneButton.setBackgroundColor(0xFFfdd835); // Yellow
            } else {
                doneButton.setBackgroundColor(0xFF1b5e20); // Dark Green
            }
        }

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chain chain = chains.get(position);

                if (chain.getDateValue(todayString).equals("D")) {
                    chain.setDone(todayString, "");
                } else {
                    chain.setDone(todayString, "Done");
                }

                ChainManager chainManager = new ChainManager(context);
                chainManager.addOrUpdateChain(chain);
                notifyDataSetChanged();
            }
        });



        return rowView;
    }
}
