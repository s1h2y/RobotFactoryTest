package com.alpha.smart.factorytest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by shy on 16-5-19.
 */
public class Result {
    private static HashMap<String, Boolean> mResMap = new HashMap<>();
    private static String testFile = "factory_test_result";

    public static void passed(String key) {
        mResMap.remove(key);
    }

    public static void failed(String key) {
        mResMap.put(key, false);
    }

    public static boolean get(String key) {
        if (null != mResMap.get(key)) {
            return mResMap.get(key).booleanValue();
        }

        return true;
    }

    public static void saveResult(Context ctx) {
        String path = getFileName();
        JSONObject json = mapToJson(mResMap);
        jsonToFile(json, path);
        SharedPreferences sp = ctx.getSharedPreferences("test", ctx.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(testFile, path);
        editor.commit();
    }

    public static void getResult(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("test", ctx.MODE_PRIVATE);
        String path = sp.getString(testFile, "");
        JSONObject json = fileToJson(path);
        mResMap = jsonToMap(json);
        MyLog.d(json.toString());
    }

    private static void checkFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFileName() {
        String path = null;
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        path = dir + "/" + getTime() + ".txt";
        checkFile(path);
        return path;
    }

    private static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String t = format.format(new Date());
        return t;
    }


    private static HashMap<String, Boolean> getResultMap() {
        HashMap<String, Boolean> map = new HashMap<>();

        return map;
    }

    private static void jsonToFile(JSONObject json, String path) {
        String content = json.toString();
        try {
            FileOutputStream fos = new FileOutputStream(path);
            byte[] bytes = content.getBytes();
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject fileToJson(String path) {
        JSONObject json = null;
        String content = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            content = buffer.toString();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            json = new JSONObject(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyLog.d(json.toString());
        return json;
    }

    private static HashMap<String, Boolean> jsonToMap(JSONObject jsonObject) {
        HashMap<String, Boolean> map = new HashMap<>();
        Iterator it = jsonObject.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            Boolean value = null;
            try {
                value = (Boolean) jsonObject.get(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            map.put(key, value);
        }
        return map;
    }

    private static JSONObject mapToJson(HashMap<String, Boolean> map) {
        Iterator it = map.entrySet().iterator();
        JSONObject resJson = new JSONObject();
        while (it.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) it.next();
            String key = (String) entry.getKey();
            Boolean value = (Boolean) entry.getValue();
            try {
                resJson.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return resJson;
    }
}
