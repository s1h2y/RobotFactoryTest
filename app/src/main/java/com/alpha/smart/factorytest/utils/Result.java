package com.alpha.smart.factorytest.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.json.JSONArray;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by shy on 16-5-19.
 */
public class Result {
    private static HashMap<String, String> mResMap = new HashMap<>();

    public static void passed(String key) {
        mResMap.put(key, Constant.PASSED);
    }

    public static void failed(String key) {
        mResMap.put(key, Constant.FAILED);
    }

    public static String get(String key) {
        if (null != mResMap.get(key)) {
            return (String)mResMap.get(key);
        }
        return null;
    }

    public static void saveResult(Context ctx) {
        String path = getFileName();
        JSONObject json = mapToJson(mResMap);
        jsonToFile(json, path);
        SharedPreferences sp = ctx.getSharedPreferences("test", ctx.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Constant.TEST_FILE, path);
        MyLog.d("saveing file " + path);
        editor.commit();
    }

    public static void getResult(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("test", ctx.MODE_PRIVATE);
        String path = sp.getString(Constant.TEST_FILE, "");
        MyLog.d("get file " + path);
        if (null != path && !path.isEmpty() && !path.equals("")) {
            JSONObject json = fileToJson(path);
            mResMap = jsonToMap(json);
        }
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
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constant.testdir;
        File f = new File(dir);
        if (!f.exists()) {
            MyLog.d("dir " + dir + " not exits, create");
            f.mkdirs();
        }
        path = dir + "/" + getTime() + ".txt";
        checkFile(path);
        return path;
    }

    private static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String t = format.format(new Date());
        return t;
    }


    private static HashMap<String, String> getResultMap() {
        HashMap<String, String> map = new HashMap<>();

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
        String content = null;
        JSONObject json = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            content = new String(buffer);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyLog.d(content.toString());
        try {
            json = new JSONObject(content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    private static HashMap<String, String> jsonToMap(JSONObject jsonObject) {
        HashMap<String, String> map = new HashMap<>();
        if (null != jsonObject) {
            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = null;
                try {
                    value = (String)jsonObject.get(key);
                    map.put(key, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    private static JSONObject mapToJson(HashMap<String, String> map) {
        Iterator it = map.entrySet().iterator();
        JSONObject resJson = new JSONObject();
        while (it.hasNext()) {
            HashMap.Entry entry = (HashMap.Entry) it.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            try {
                resJson.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return resJson;
    }

    public static boolean isOK(Context ctx) {
        getResult(ctx);
        if (mResMap.isEmpty()) {
            return true;
        } else {
            Iterator it = mResMap.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry entry = (Map.Entry)it.next();
                String val = (String)entry.getValue();
                if (val.equals(Constant.FAILED)) {
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean isChecked(Context ctx) {
        SharedPreferences sp = ctx.getSharedPreferences("test", ctx.MODE_PRIVATE);
        String path = sp.getString(Constant.TEST_FILE, "none");
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static void clear() {
        mResMap.clear();
    }
}
