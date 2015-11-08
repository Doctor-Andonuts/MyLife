package com.andonuts.mylife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Andonuts on 11/7/2015.
 */
public class CustomArrayAdapter extends ArrayAdapter<Chain> {
    private final Context context;
    private final List<Chain> chains;

    public CustomArrayAdapter(Context context, List<Chain> chains) {
        super(context, -1, chains);
        this.context = context;
        this.chains = chains;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.chain_list_item, parent, false);

        TextView descriptionTextView = (TextView) rowView.findViewById(R.id.description);
        descriptionTextView.setText(chains.get(position).getTitle());

        Button button= (Button) rowView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(context, "Clicked Button: " + chains.get(position).getTitle() , Toast.LENGTH_SHORT).show();
                // Your code that you want to execute on this button click
            }

        });

        return rowView;
    }
}
