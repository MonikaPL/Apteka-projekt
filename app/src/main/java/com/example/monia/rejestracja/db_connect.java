package com.example.monia.rejestracja;

import org.json.JSONArray;

import java.io.InputStream;

import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class db_connect {

    public JSONArray getResults(String query) {
        String result = "";
        //the year data to send
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("query", query));
        InputStream is = null;
//http post
        try{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new
                    HttpPost("http://212.182.24.105:8010/db_connect/get.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }catch(Exception e){
            Log.e("log_tag", "Error in http connection "+e.toString());
        }
//convert response to string
        try{
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result=sb.toString();
        }catch(Exception e){
            Log.e("log_tag", "Error converting result "+e.toString());
        }
        try{
            JSONArray jArray = new JSONArray(result);
            return jArray;
        } catch(JSONException e){
            Log.e("log_tag", "Error parsing data "+e.toString());
        }
        return null;
    }
}

