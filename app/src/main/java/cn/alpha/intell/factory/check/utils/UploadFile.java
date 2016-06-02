package cn.alpha.intell.factory.check.utils;

import android.os.Handler;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by shy on 16-5-31.
 */
public class UploadFile {

    private String mToken;
    private String mGetUploadUrl = "http://api.robot.yoozi.cn/m/sign/policy";
    private UploadPolicy mPolicy;
    private Handler mHandler;
    private File mFile;

    private String mPhone = "18854332800";
    private String mPwd = "123456";

    public void commitResult(File file, Handler handler) {
        this.mFile = file;
        this.mHandler = handler;
        getToken();
    }

    public class Token {
        public String token;
    }

    private void getToken() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("mobile", mPhone);
        params.put("password", mPwd);
        client.addHeader("Accept", "application/vnd.robot.v1+json");
        client.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        client.post("http://api.robot.yoozi.cn/m/token", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Token t = new Gson().fromJson(new String(responseBody), Token.class);
                mToken = t.token;
                MyLog.d("get token = " + mToken);
                getUploadParam();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                onError(statusCode, responseBody);
            }
        });
    }

    private void onError(int statusCode, byte[] responseBody) {
        if (null != responseBody) {
            try {
                MyLog.d("why fail " + statusCode + ", " + new String(responseBody, "utf8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            MyLog.d("why fail code " + statusCode);
        }
        if (null != mHandler) {
            mHandler.sendEmptyMessage(0);
        }
    }

    private void getUploadParam() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.addHeader("Authorization", "Bearer " + mToken);
        client.addHeader("Accept", "application/vnd.robot.v1+json");
        client.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        params.put("policy", "avatar");
        client.get(mGetUploadUrl, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mPolicy = new Gson().fromJson(new String(responseBody), UploadPolicy.class);
                MyLog.d("get policy " + mPolicy.toString());
//                uploadFile();
                httpurlUpload();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("getUploadParam failed");
                onError(statusCode, responseBody);
            }
        });
    }


    private void httpurlUpload() {
        changeFile();
        Map<String, String> payload = new HashMap<String, String>();
        payload.put("policy", mPolicy.policy);
        payload.put("key", mPolicy.dir + mPolicy.filename + ".txt");
        payload.put("OSSAccessKeyId", mPolicy.accessid);
        payload.put("success_action_status", "200");
        payload.put("callback", mPolicy.callback);
        payload.put("signature", mPolicy.signature);
        payload.put("name", mPolicy.filename + ".txt");
        payload.put("file", mFile.getPath());
        payload.put("type", "text/plain");
        try {
            new MHttpPost().postMultiData(mHandler, mPolicy.host, mFile, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadFile() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Connection", "keep-alive");
//        client.addHeader("Content-Type", "multipart/form-data");

        RequestParams params = new RequestParams();

        changeFile();

        params.put("OSSAccessKeyId", mPolicy.accessid);
        params.put("signature", mPolicy.signature);
        params.put("success_action_status", "200");
        params.put("name", mPolicy.filename + ".jpg");
        params.put("callback", mPolicy.callback);
        params.put("key", mPolicy.dir + mPolicy.filename + ".jpg");
        params.put("policy", mPolicy.policy);
        try {
            params.put("file", mFile, "image/jpeg", mPolicy.filename + ".jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (null != mHandler) {
                mHandler.sendEmptyMessage(0);
            }
        }
        params.setForceMultipartEntityContentType(false);

        MyLog.d("66666666    " + params.toString());
        client.post(mPolicy.host, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (null != mHandler) {
                    mHandler.sendEmptyMessage(1);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyLog.d("uploadFile failed");
                for (int i = 0; i < headers.length; i++) {
                    MyLog.d("headers: " + headers[i]);
                }
                onError(statusCode, responseBody);
            }
        });
    }

    private void changeFile() {
        String path = mFile.getPath();
        MyLog.d("origin path= " + path);
        MyLog.d(path.substring(0, path.lastIndexOf("/")));
        path = path.substring(0, path.lastIndexOf("/")) + "/" + mPolicy.filename + ".txt";
        copy(mFile, new File(path));
        mFile = new File(path);
        MyLog.d("new file " + mFile.getPath());
    }

    public static void copy(File source, File target) {
        File tarpath = target;
        if (source.isDirectory()) {
            tarpath.mkdir();
            File[] dir = source.listFiles();
            for (int i = 0; i < dir.length; i++) {
                copy(dir[i], tarpath);
            }
        } else {
            try {
                InputStream is = new FileInputStream(source);
                OutputStream os = new FileOutputStream(tarpath);
                byte[] buf = new byte[1024];
                int len = 0;
                while ((len = is.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                is.close();
                os.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
