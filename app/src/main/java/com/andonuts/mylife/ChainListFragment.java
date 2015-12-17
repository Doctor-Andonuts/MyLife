package com.andonuts.mylife;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChainListFragment extends ListFragment {

    // TODO: Start working in ideas I have about PerWeek and MinMax display.  Adjust the XML display and CustomAdapter to display those things

    private List<Chain> chains = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chain_list, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        refreshData();

        CustomArrayAdapter arrayAdapter = new CustomArrayAdapter(getActivity(), chains);
        arrayAdapter.notifyDataSetChanged();
        setListAdapter(arrayAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.e("DETAILCLICK", "Item Clicked: " + id);
        Log.e("DETAILCLICK", "Clicked: " + chains.get(position).getTitle());

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(chains.get(position));
        }
    }


    public void refreshData() {
        ChainManager chainManager = new ChainManager(getActivity());
        chains.clear();
        chains.addAll(chainManager.getChains());
        Log.d("FragmentList", "DATA REFRESH");
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Chain chain);
    }

}
