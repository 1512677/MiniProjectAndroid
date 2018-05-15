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
import java.util.ArrayList;

/**
 * Created by TrungVinh on 5/16/2018.
 */

public class BookmarkManager {
    private static final String mfilename = "savebookmark.json";
    public static ArrayList<String> mName = new ArrayList<String>();
    public static ArrayList<String> mAddress = new ArrayList<String>();

    public static void initBookmarkManager(Context context) {
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
            String data = "";
            int c;
            while ((c = inputStream.read()) != -1) {
                data = data + Character.toString((char) c);
            }
            Log.e("dataReadInit", data);
            // Convert json abject to data hashmap
            JSONObject reader = new JSONObject(data);
            JSONArray arrayData = reader.getJSONArray("arrayDataBookmark");
            for (int i = 0; i < arrayData.length(); i++) {
                JSONObject mem = arrayData.getJSONObject(i);
                addItem(mem.getString("name"), mem.getString("address"));
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean addItem(String name, String address) {
        boolean isExist = false;
        for (int i = 0; i < mAddress.size(); i++) {
            if (address.equals(mAddress.get(i))) {
                isExist = true;
                return false;
            }
        }
        if (!isExist) {
            mName.add(name);
            mAddress.add(address);
        }
        return true;
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
        String data = "{\"arrayDataBookmark\":[";
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
