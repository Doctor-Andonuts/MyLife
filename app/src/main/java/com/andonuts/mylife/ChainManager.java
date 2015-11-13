package com.andonuts.mylife;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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
    private String fileName = "chain.data";
    private Context _context;

    public ChainManager(Context context)
    {
        _context = context;
    }

    public List<Chain> getChains() {
        List<Chain> returnChainList = new ArrayList<>();
        HashMap<String, Chain> hashMapChainList = readFile();

        for (Chain chain : hashMapChainList.values()) {
            returnChainList.add(chain);
        }

        return returnChainList;
    }

    private HashMap<String, Chain> readFile() {
        HashMap<String, Chain> chainHashmap = new HashMap<>();

        try {
            File file = new File(_context.getFilesDir(), fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                Chain chain = new Chain(new JSONObject(line));
                chainHashmap.put(chain.getUUID(), chain);
            }
        } catch (Exception e ) {
            Log.d(TAG, "Problem reading " + fileName + ": " + e.toString());
        }

        Log.d(TAG, "Done reading " + fileName);
        return chainHashmap;
    }

    public void addOrUpdateChain(Chain newChain) {
        HashMap<String, Chain> chainList = readFile();
        chainList.put(newChain.getUUID(), newChain);
        writeFile(chainHashMapToString(chainList));
    }


    private String chainHashMapToString(HashMap<String, Chain> chainHashMap) {
        String returnString = "";
        for (Chain chain : chainHashMap.values()) {
            returnString += chain.getJsonString() + "\n";
        }

        return returnString;
    }

    private void writeFile(String chainData) {
        try {
            FileOutputStream fileOutputStream = _context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(chainData.getBytes());
            fileOutputStream.close();
            Log.d("ChainListFile", "Done Writing data to " + fileName);
        } catch (Exception e) {
            Log.d("ChainListFile", "Problem writing data to : " + fileName + e.toString());
        }
    }

}
