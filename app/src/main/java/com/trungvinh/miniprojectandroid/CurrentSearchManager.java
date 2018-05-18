package com.trungvinh.miniprojectandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by TrungVinh on 5/16/2018.
 */

public class CurrentSearchManager {
    private static final String mfilename = "savecurrentsearch.json";
    public static ArrayList<String> mName = new ArrayList<String>();
    public static ArrayList<String> mAddress = new ArrayList<String>();

    public static void initCurrentSearchManager(Context context) {
        AppCompatActivity mMainActivity = (AppCompatActivity) context;
        try {
            //mMainActivity.deleteFile(mfilename);
            if (!fileExists(context, mfilename)) {
                File file = new File(mMainActivity.getFilesDir(), mfilename);
                addItem("empty", "empty");
                UpdateFile(context);
            }
            FileInputStream inputStream = mMainActivity.openFileInput(mfilename);
            // read all data for file
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String data = "";
            String line;
            while ((line = br.readLine()) != null) {
                data += line;
                data += '\n';
            }
            Log.e("dataReadInit", data);
            // Convert json abject to data hashmap
            JSONObject reader = new JSONObject(data);
            JSONArray arrayData = reader.getJSONArray("arrayDataCurrent");
            for (int i = 0; i < arrayData.length(); i++) {
                JSONObject mem = arrayData.getJSONObject(i);
                addItem(mem.getString("name"), mem.getString("address"));
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void initData(Context context) {
        mName.clear();
        mAddress.clear();
        addItem("empty", "empty");
        UpdateFile(context);
        addItem("empty", "empty");
    }

    public static void addItem(String name, String address) {
        mName.add(name);
        mAddress.add(address);
    }

    public static void removeItem(int index) {
        mName.remove(index);
        mAddress.remove(index);
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
        String data = "{\"arrayDataCurrent\":[";
        for (int i = 0; i < mName.size(); i++) {
            String name = mName.get(i);
            String address = mAddress.get(i);
            String arrayElement = "\n{\n" +
                    "\t\"name\":\"" + name + "\",\n" +
                    "\t\"address\":\"" + address + "\"\n" +
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
        mName.clear();
        mAddress.clear();
    }
}
