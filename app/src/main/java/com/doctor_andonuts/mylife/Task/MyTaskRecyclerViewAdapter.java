package com.doctor_andonuts.mylife.Task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doctor_andonuts.mylife.R;
import com.doctor_andonuts.mylife.Task.TaskListFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {

    private final List<Task> tasks;
    private final OnListFragmentInteractionListener mListener;

    public MyTaskRecyclerViewAdapter(List<Task> tasks, OnListFragmentInteractionListener listener) {
        this.tasks = tasks;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = tasks.get(position);
        holder.mDescriptionView.setText(tasks.get(position).getValue("description"));
        holder.mProjectView.setText(tasks.get(position).getValue("project"));
        String urgencyString = String.format("%.2f", tasks.get(position).getUrgency());
        holder.mUrgencyView.setText(urgencyString);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDescriptionView;
        public final TextView mProjectView;
        public final TextView mUrgencyView;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDescriptionView = (TextView) view.findViewById(R.id.task_card_description);
            mProjectView = (TextView) view.findViewById(R.id.task_card_project);
            mUrgencyView = (TextView) view.findViewById(R.id.task_card_urgency);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDescriptionView.getText() + "'";
        }
    }
}
