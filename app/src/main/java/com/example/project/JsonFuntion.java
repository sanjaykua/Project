package com.example.project;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonFuntion {
    public static JSONObject DataHandle(String url,String param){
        JSONObject jsonObject=null;
        try {
            URL jurl=new URL(url);
            HttpURLConnection connection= (HttpURLConnection) jurl.openConnection();

            //Ccnditions
            connection.setConnectTimeout(6000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            //headers
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Accept-Charset","UTF-8");
            connection.setRequestProperty("Accept","application/json");
            connection.connect();

            OutputStream outputStream=new BufferedOutputStream(connection.getOutputStream());
            outputStream.write(param.getBytes());
            outputStream.flush();

            InputStream inputStream=new BufferedInputStream(connection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuffer stringBuffer=new StringBuffer();

            while ((line=bufferedReader.readLine())!=null){
                stringBuffer.append(line);
            }
            inputStream.close();
            jsonObject=new JSONObject(stringBuffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
