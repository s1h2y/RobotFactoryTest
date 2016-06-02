package cn.alpha.intell.factory.check.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import android.os.Handler;
import android.util.Log;

public class MHttpPost {
    // 每个post参数之间的分隔。随意设定，只要不会和其他的字符串重复即可。
//    private static final String BOUNDARY = "rLPF_1PhfzQwE4npWwevtuHX-HkTHO";
    private static final String BOUNDARY = getRandomString(40);
    private static final String HEAD_CONTENT_TYPE = "Content-Type";
    private static final String HEAD_FORM = "multipart/form-data; boundary=" + BOUNDARY;
    private static final String BODY_FORM_END = "\r\n" + "--" + BOUNDARY + "--" + "\r\n";
    private static final String BODY_FORM_ITEM_BOUNDARY = "--" + BOUNDARY;
    private static final String BODY_FORM_KEY_PREFIX = "\r\n" + "Content-Disposition: form-data; name=" + "\"";
    private static final String BODY_FORM_KEY_SUFFIX = "\"" + "\r\n" + "\r\n";
    private static final String BODY_FORM_KEY_FILENAME_PREFIX = "\"" + "; filename=" + "\"";
    private static final String BODY_FORM_KEY_FILENAME_SUFFIX = "\"" + "\r\n";
    private static final String BODY_FORM_CONTENT_TYPE_PREFIX = "Content-Type: ";
    private static final String BODY_FORM_CONTENT_TRANSFER_ENCODEING = "\r\n"
            + "Content-Transfer-Encoding: binary" + "\r\n" + "\r\n";

    private StringBuilder mContentBody = new StringBuilder();

    public void putKeyValue(String key, String value) {
        mContentBody.append(BODY_FORM_ITEM_BOUNDARY)
                .append(BODY_FORM_KEY_PREFIX)
                .append(key)
                .append(BODY_FORM_KEY_SUFFIX)
                .append(value).append("\r\n");
    }

    public void putFile(String key, File file, String type) {
        mContentBody.append(BODY_FORM_ITEM_BOUNDARY)
                .append(BODY_FORM_KEY_PREFIX)
                .append(key)
                .append(BODY_FORM_KEY_FILENAME_PREFIX)
                .append(file.getName())
                .append(BODY_FORM_KEY_FILENAME_SUFFIX)
                .append(BODY_FORM_CONTENT_TYPE_PREFIX)
                .append(type)
                .append(BODY_FORM_CONTENT_TRANSFER_ENCODEING);
    }

    public void sendHttpPostRequest(final Handler handler, final String serverUrl,
                                    final File file, final Map<String, String> payload) throws Exception {

        new Thread(new Runnable() {

            public void run() {
                try {
                    URL url = new URL(serverUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 发送POST请求必须设置如下两行
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Connection", "keep-alive");
                    connection.setRequestProperty("Content-Type",
                            "multipart/form-data; boundary=" + BOUNDARY);
                    // connection.setRequestProperty("Host", host);
                    // 头
                    String boundary = BOUNDARY;
                    // 传输内容
                    StringBuffer contentBody = new StringBuffer("--" + BOUNDARY);
                    // 尾
                    String endBoundary = "\r\n--" + boundary + "--\r\n";
                    OutputStream out = connection.getOutputStream();
                    // 1. 处理文字形式的POST请求
                    String OSSAccessKeyId = "\r\n" + "Content-Disposition: form-data; name=\"OSSAccessKeyId\"" + "\r\n" + "\r\n" + payload.get("OSSAccessKeyId");
                    contentBody.append(OSSAccessKeyId).append("\r\n").append("--").append(boundary);

                    String signature = "\r\n" + "Content-Disposition: form-data; name=\"signature\"" + "\r\n" + "\r\n" + payload.get("signature");
                    contentBody.append(signature).append("\r\n").append("--").append(boundary);

                    String success_action_status = "\r\n" + "Content-Disposition: form-data; name=\"success_action_status\"" + "\r\n" + "\r\n" + payload.get("success_action_status");
                    contentBody.append(success_action_status).append("\r\n").append("--").append(boundary);

                    contentBody.append("\r\n")
                            .append("Content-Disposition: form-data; name=\"")
                            .append("name" + "\"")
                            .append("\r\n")
                            .append("\r\n")
                            .append(payload.get("name"))
                            .append("\r\n")
                            .append("--")
                            .append(boundary);

                    String callback = "\r\n" + "Content-Disposition: form-data; name=\"callback\"" + "\r\n" + "\r\n" + payload.get("callback");
                    contentBody.append(callback).append("\r\n").append("--").append(boundary);

                    String key = "\r\n" + "Content-Disposition: form-data; name=\"key\"" + "\r\n" + "\r\n" + payload.get("key");
                    contentBody.append(key).append("\r\n").append("--").append(boundary);

                    String policy = "\r\n" + "Content-Disposition: form-data; name=\"policy\"" + "\r\n" + "\r\n" + payload.get("policy");
                    contentBody.append(policy).append("\r\n").append("--").append(boundary);

                    contentBody.append("\r\n")
                            .append("Content-Disposition: form-data; name=\"")
                            .append("file" + "\"; ") // form中field的名称
                            .append("filename=\"")
                            .append(file.getName() + "\"") // 上传文件的文件名，包括目录
                            .append("\r\n")
                            .append("Content-Type: text/plain")
                            .append("\r\n")
                            .append("Content-Transfer-Encoding: binary")
                            .append("\r\n\r\n");

                    out.write(contentBody.toString().getBytes("utf-8"));
                    MyLog.d("shy1111111  " + contentBody.toString());
                    // 开始真正向服务器写文件
                    DataInputStream dis = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[(int) file.length()];
                    bytes = dis.read(bufferOut);
                    out.write(bufferOut, 0, bytes);
                    dis.close();
                    // 3. 写结尾
                    out.write(endBoundary.getBytes("utf-8"));
                    out.flush();
                    out.close();
                    // 4. 从服务器获得回答的内容
                    String strLine = "";
                    String strResponse = "";
                    Log.e("MHttpPost", "response code:" + connection.getResponseCode() + connection.getResponseMessage());
                    try {
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        while ((strLine = reader.readLine()) != null) {
                            strResponse += strLine + "\n";
                        }
                        handler.sendEmptyMessage(1);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        InputStream error = connection.getErrorStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(error));
                        while ((strLine = reader.readLine()) != null) {
                            strResponse += strLine + "\n";
                        }
                        handler.sendEmptyMessage(0);
                    }


                    Log.e("upload", strResponse);
                    // System.out.print(strResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0);
                }

            }
        }).start();
    }

    public void mapToBody(Map<String, String> payload) {
        Iterator<String> it = payload.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (key.equals("file") || key.equals("type")) {
                continue;
            }
            putKeyValue(key, payload.get(key));
        }
    }

    public void postMultiData(final Handler handler, final String serverUrl,
                              final File file, final Map<String, String> payload) throws Exception {

        new Thread(new Runnable() {

            public void run() {
                try {
                    URL url = new URL(serverUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    // 发送POST请求必须设置如下两行
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Connection", "keep-alive");
                    connection.setRequestProperty("Content-Type",
                            "multipart/form-data; boundary=" + BOUNDARY);
                    OutputStream out = connection.getOutputStream();

                    // write form item except file
                    mapToBody(payload);

                    // write form item file
                    putFile("file", file, payload.get("type"));

                    out.write(mContentBody.toString().getBytes("utf-8"));

                    // write file binary
                    DataInputStream dis = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[(int) file.length()];
                    bytes = dis.read(bufferOut);
                    out.write(bufferOut, 0, bytes);
                    dis.close();
                    // write form end
                    out.write(BODY_FORM_END.getBytes("utf-8"));
                    out.flush();
                    out.close();
                    // get server response
                    String strLine = "";
                    String strResponse = "";
                    Log.e("MHttpPost", "response code:" + connection.getResponseCode() + connection.getResponseMessage());
                    try {
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        while ((strLine = reader.readLine()) != null) {
                            strResponse += strLine + "\n";
                        }
                        handler.sendEmptyMessage(1);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        InputStream error = connection.getErrorStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(error));
                        while ((strLine = reader.readLine()) != null) {
                            strResponse += strLine + "\n";
                        }
                        handler.sendEmptyMessage(0);
                    }


                    Log.e("upload", strResponse);
                    // System.out.print(strResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(0);
                }

            }
        }).start();
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
