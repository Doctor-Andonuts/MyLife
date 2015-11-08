package com.andonuts.mylife;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Andonuts on 11/7/2015.
 */
public class ChainManager {
    private String TAG = "ChainManager";
    String fileName = "chain.data";
    private Context _context;

    public ChainManager(Context context)
    {
        _context = context;
    }

    public List<Chain> getChains() {
        List<Chain> returnTaskList = new ArrayList<>();

        try {
            File file = new File(_context.getFilesDir(), fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                Chain chain = new Chain(new JSONObject(line));
                returnTaskList.add(chain);
            }
        } catch (Exception e ) {
            Log.d(TAG, "Problem reading " + fileName + ": " + e.toString());
        }

        Log.d(TAG, "Done reading " + fileName);
        return returnTaskList;
    }
}
