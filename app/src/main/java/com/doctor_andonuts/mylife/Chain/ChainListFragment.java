package com.doctor_andonuts.mylife.Chain;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.doctor_andonuts.mylife.R;

import java.util.ArrayList;
import java.util.List;

public class ChainListFragment extends ListFragment {
    private final List<Chain> chains = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_chain_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
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

        ChainListArrayAdapter arrayAdapter = new ChainListArrayAdapter(getActivity(), chains, mListener);
        arrayAdapter.notifyDataSetChanged();
        setListAdapter(arrayAdapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final CharSequence colors[] = new CharSequence[] {"Set Vacation Day", "Set Sick Day"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // the user clicked on colors[which]
                        Toast.makeText(getContext(), "Ressult: " + which, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                return false;
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.item_resort:
                refreshData();
                ChainListArrayAdapter arrayAdapter = new ChainListArrayAdapter(getActivity(), chains, mListener);
                arrayAdapter.notifyDataSetChanged();
                setListAdapter(arrayAdapter);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void refreshData() {
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
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Chain chain);
    }

}
