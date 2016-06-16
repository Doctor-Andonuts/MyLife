package com.doctor_andonuts.mylife.Chain;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        doneButton.setTextColor(Color.WHITE);

        //if (chain.getType().equals("MinMax")) {
            String dayStatus = chain.getDayStatus(todayString);
            switch(dayStatus) {
                case "Done":
                    doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.done));
                    doneButton.setTextColor(Color.WHITE);
                    break;
                case "Off":
                    doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.off));
                    doneButton.setTextColor(Color.WHITE);
                    break;
                case "Should do":
                    doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.shouldDo));
                    doneButton.setTextColor(Color.BLACK);
                    break;
                case "No need":
                    doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.noNeed));
                    doneButton.setTextColor(Color.WHITE);
                    break;
                case "DO IT!":
                    doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.doIt));
                    doneButton.setTextColor(Color.WHITE);
                    break;
                default:
                    doneButton.setBackgroundColor(0xFF666666);
                    doneButton.setTextColor(Color.WHITE);
                    break;
            }
//        } else {
//            double[] onceOverData = chain.getOnceOverData(todayString);
//            if (onceOverData[0] == -1 && onceOverData[1] == -1) {
//                doneButton.setBackgroundColor(0xFF43a047); // Light Green
//            } else if (onceOverData[0] >= onceOverData[1]) {
//                doneButton.setBackgroundColor(0xFFc62828); // Red
//            } else if (onceOverData[0] / onceOverData[1] >= 0.5) {
//                doneButton.setBackgroundColor(0xFFfdd835); // Yellow
//            } else {
//                doneButton.setBackgroundColor(0xFF1b5e20); // Dark Green
//            }
//        }

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chain chain = chains.get(position);

                if (chain.getDateValue(todayString).equals("D")) {
                    chain.setDone(todayString, "");
                } else {
                    chain.setDone(todayString, "Done");
                }
                refreshChainListItem(chain, rowView);
            }
        });

        doneButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                final CharSequence colors[] = new CharSequence[] {"Set Vacation Day", "Set Sick Day", "Set Off Day"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Chain chain = chains.get(position);

                        if (which == 0) {
                            chain.setDone(todayString, "Vacation");
                            Log.d("VACATION", chain.getDateValue(todayString));
                        } else if (which == 1) {
                            chain.setDone(todayString, "Sick");
                        } else {
                            chain.setDone(todayString, "Off Day");
                        }
                        refreshChainListItem(chain, rowView);
                    }
                });
                builder.show();
                return true;
            }
        });


        return rowView;
    }

    private void refreshChainListItem(Chain chain, View rowView) {
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        final Calendar today = Calendar.getInstance();
        final String todayString = myDateFormat.format(today.getTime());

        TextView onceOverTextView = (TextView) rowView.findViewById(R.id.onceOver);
        onceOverTextView.setText(chain.getOnceOverString(todayString));

        Button doneButton = (Button) rowView.findViewById(R.id.listButton_Done);
        doneButton.setText(String.valueOf(chain.getCurrentLength(todayString)));
        doneButton.setTextColor(0xFFFFFFFF);

        if (chain.getType().equals("MinMax")) {
            String dayStatus = chain.getDayStatus(todayString);

            switch(dayStatus) {
                case "Done":
                    doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.done)); // Light Green
                    doneButton.setTextColor(Color.WHITE);
                    break;
                case "Off":
                    doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.off)); // Blue
                    doneButton.setTextColor(Color.WHITE);
                    break;
                case "Should do":
                    doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.shouldDo)); // Yellow
                    doneButton.setTextColor(Color.BLACK);
                    break;
                case "No need":
                    doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.noNeed)); // Dark Green
                    doneButton.setTextColor(Color.WHITE);
                    break;
                case "DO IT!":
                    doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.doIt)); // Red
                    doneButton.setTextColor(Color.WHITE);
                    break;
                default:
                    doneButton.setBackgroundColor(0xFF666666);
                    break;
            }
        } else {
            double[] onceOverData = chain.getOnceOverData(todayString);
            if (onceOverData[0] == -1 && onceOverData[1] == -1) {
                doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.done)); // Light Green
                doneButton.setTextColor(Color.WHITE);
            } else if (onceOverData[0] == onceOverData[1]) {
                doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.doIt)); // Red
                doneButton.setTextColor(Color.WHITE);
            } else if (onceOverData[0] / onceOverData[1] >= 0.5) {
                doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.shouldDo)); // Yellow
                doneButton.setTextColor(Color.BLACK);
            } else {
                doneButton.setBackgroundColor(getContext().getResources().getColor(R.color.noNeed)); // Dark Green
                doneButton.setTextColor(Color.WHITE);
            }
        }

        ChainManager chainManager = new ChainManager(context);
        chainManager.addOrUpdateChain(chain);

        Intent intent = new Intent(context, ChainWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, ChainWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
        context.sendBroadcast(intent);
    }
}
