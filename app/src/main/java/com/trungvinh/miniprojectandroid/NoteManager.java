package com.trungvinh.miniprojectandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by TrungVinh on 5/15/2018.
 */

public class NoteManager {
    private static final String mfilename = "savenote.json";
    private static HashMap<String, String> mDataMap = new HashMap<String, String>();

    public static void initNoteManager(Context context) {
        AppCompatActivity mMainActivity = (AppCompatActivity) context;
        try {
            //mMainActivity.deleteFile(mfilename);
            if (!fileExists(context, mfilename)) {
                File file = new File(mMainActivity.getFilesDir(), mfilename);
                setNotebyId("-1", "empty");
                UpdateFile(context);
            }
            FileInputStream inputStream = mMainActivity.openFileInput(mfilename);
            // read all data for file
            String data = "";
            int c;
            while ((c = inputStream.read()) != -1) {
                data = data + Character.toString((char) c);
            }
            Log.e("dataReadInit", data);
            // Convert json abject to data hashmap
            JSONObject reader = new JSONObject(data);
            JSONArray arrayData = reader.getJSONArray("arrayData");
            for (int i = 0; i < arrayData.length(); i++) {
                JSONObject mem = arrayData.getJSONObject(i);
                mDataMap.put(mem.getString("id"), mem.getString("note"));
            }
            //inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setNotebyId(String id, String note) {
        mDataMap.put(id, note);
        //return "Note here";
    }

    public static String getNotebyId(String s) {
        return mDataMap.get(s);
        //return "Note here";
    }

    public static boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    public static void UpdateFile(Context context) {
        AppCompatActivity mMainActivity = (AppCompatActivity) context;
        // Prepare data
        String data = "{\"arrayData\":[";
        Iterator myVeryOwnIterator = mDataMap.keySet().iterator();
        while (myVeryOwnIterator.hasNext()) {
            String id = (String) myVeryOwnIterator.next();
            String note = (String) mDataMap.get(id);
            String arrayElement = "\n{\n" +
                    "\t\"id\":\"" + id + "\",\n" +
                    "\t\"note\":\"" + note + "\"\n" +
                    "},";
            data += arrayElement;
        }
        data = data.substring(0, data.length() - 1);
        data += "]}";
        FileOutputStream outputStream = null;
        try {
            outputStream = mMainActivity.openFileOutput(mfilename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            outputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("dataUpdateFile", data);
    }

    private static void LogDataCurrent() {
        String data = "";
        Iterator myVeryOwnIterator = mDataMap.keySet().iterator();
        while (myVeryOwnIterator.hasNext()) {
            String id = (String) myVeryOwnIterator.next();
            String note = (String) mDataMap.get(id);
            String arrayElement = id + ':' + note + '\n';
            data += arrayElement;
        }
        Log.e("LogDataCurrent: ", data);
    }
}
