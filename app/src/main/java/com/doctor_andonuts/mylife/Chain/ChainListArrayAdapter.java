package com.doctor_andonuts.mylife.Chain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.doctor_andonuts.mylife.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Andonuts on 11/7/2015.
 *
 * Array adapter for the list fragment
 */
class ChainListArrayAdapter extends ArrayAdapter<Chain> {
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

    public ChainListArrayAdapter(Context context, List<Chain> chains, ChainListFragment.OnFragmentInteractionListener mListener) {
        super(context, -1, chains);
        this.context = context;
        this.chains = chains;
        this.mListener = mListener;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") final View rowView = inflater.inflate(R.layout.chain_list_item, parent, false);

        Chain chain = chains.get(position);

        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final Calendar today = Calendar.getInstance();
        final String todayString = myDateFormat.format(today.getTime());
        Log.d("DateCheck", todayString);

        TextView descriptionTextView = (TextView) rowView.findViewById(R.id.description);
        descriptionTextView.setText(chain.getTitle());
        descriptionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chain chain = chains.get(position);
                mListener.onFragmentInteraction(chain);
            }
        });


// -------------------------------------

        String startDateString = chain.getStartDate();
        String endDateString = chain.getEndDate();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        try {
            startDate.setTime(myDateFormat.parse(startDateString));
            if (endDateString == null || endDateString.equals("null")) {
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

        if (startDate.after(today)) {
            rowView.setBackgroundColor(0xFF999999);
        }

        if (!chain.getEndDate().equals("null") && endDate != null && endDate.before(today)) {
            rowView.setBackgroundColor(0xFF999999);
        }


// -------------------------------------


        TextView onceOverTextView = (TextView) rowView.findViewById(R.id.onceOver);
        onceOverTextView.setText(chain.getOnceOverString(todayString));

        Button doneButton = (Button) rowView.findViewById(R.id.listButton_Done);
        doneButton.setText(String.valueOf(chain.getCurrentLength(todayString)));
        doneButton.setTextColor(0xFFFFFFFF);

        if (chain.getType().equals("MinMax")) {
            String dayStatus = chain.getDayStatus(todayString);
            switch(dayStatus) {
                case "Done":
                    doneButton.setBackgroundColor(0xFF43a047); // Light Green
                    break;
                case "Should do":
                    doneButton.setBackgroundColor(0xFFfdd835); // Yellow
                    break;
                case "No need":
                    doneButton.setBackgroundColor(0xFF1b5e20); // Dark Green
                    break;
                case "DO IT!":
                    doneButton.setBackgroundColor(0xFFc62828); // Red
                    break;
                default:
                    doneButton.setBackgroundColor(0xFF666666);
                    break;
            }
        } else {
            double[] onceOverData = chain.getOnceOverData(todayString);
            if (onceOverData[0] == -1 && onceOverData[1] == -1) {
                doneButton.setBackgroundColor(0xFF43a047); // Light Green
            } else if (onceOverData[0] >= onceOverData[1]) {
                doneButton.setBackgroundColor(0xFFc62828); // Red
            } else if (onceOverData[0] / onceOverData[1] >= 0.5) {
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

                TextView onceOverTextView = (TextView) rowView.findViewById(R.id.onceOver);
                onceOverTextView.setText(chain.getOnceOverString(todayString));

                Button doneButton = (Button) rowView.findViewById(R.id.listButton_Done);
                doneButton.setText(String.valueOf(chain.getCurrentLength(todayString)));
                doneButton.setTextColor(0xFFFFFFFF);

                if (chain.getType().equals("MinMax")) {
                    String dayStatus = chain.getDayStatus(todayString);

                    switch(dayStatus) {
                        case "Done":
                            doneButton.setBackgroundColor(0xFF43a047); // Light Green
                            break;
                        case "Should do":
                            doneButton.setBackgroundColor(0xFFfdd835); // Yellow
                            break;
                        case "No need":
                            doneButton.setBackgroundColor(0xFF1b5e20); // Dark Green
                            break;
                        case "DO IT!":
                            doneButton.setBackgroundColor(0xFFc62828); // Red
                            break;
                        default:
                            doneButton.setBackgroundColor(0xFF666666);
                            break;
                    }
                } else {
                    double[] onceOverData = chain.getOnceOverData(todayString);
                    if (onceOverData[0] == -1 && onceOverData[1] == -1) {
                        v.setBackgroundColor(0xFF43a047); // Light Green
                    } else if (onceOverData[0] == onceOverData[1]) {
                        v.setBackgroundColor(0xFFc62828); // Red
                    } else if (onceOverData[0] / onceOverData[1] >= 0.5) {
                        v.setBackgroundColor(0xFFfdd835); // Yellow
                    } else {
                        v.setBackgroundColor(0xFF1b5e20); // Dark Green
                    }
                }

                ChainManager chainManager = new ChainManager(context);
                chainManager.addOrUpdateChain(chain);
            }
        });


        return rowView;
    }
}
