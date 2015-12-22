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

    public CustomArrayAdapter(Context context, List<Chain> chains) {
        super(context, -1, chains);
        this.context = context;
        this.chains = chains;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.chain_list_item, parent, false);

        Chain chain = chains.get(position);

        TextView descriptionTextView = (TextView) rowView.findViewById(R.id.description);
        descriptionTextView.setText(chain.getTitle());

        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar today = Calendar.getInstance();
        final String todayString = myDateFormat.format(today.getTime());
        Log.d("DateCheck", todayString);

        TextView onceOverTextView= (TextView) rowView.findViewById(R.id.onceOver);
        onceOverTextView.setText(chain.getOnceOver(todayString));

        Button button = (Button) rowView.findViewById(R.id.listButton_Done);

        String dayStatus = chain.getDayStatus(todayString);
        if(dayStatus.equals("Done")) {
            button.setBackgroundColor(0xFF43a047);
        } else if(dayStatus.equals("Should do")) {
            button.setBackgroundColor(0xFFfdd835);
        } else if(dayStatus.equals("No need")) {
            button.setBackgroundColor(0xFF1b5e20);
        } else if(dayStatus.equals("DO IT!")) {
            button.setBackgroundColor(0xFFc62828);
        } else {
            button.setBackgroundColor(0xFF666666);
        }

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Chain chain = chains.get(position);

                if(chain.getDateValue(todayString).equals("D")) {
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
