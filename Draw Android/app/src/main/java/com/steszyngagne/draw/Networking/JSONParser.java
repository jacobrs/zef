package com.steszyngagne.draw.Networking;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class JSONParser{

    public static String BASE_URL = "http://ec2-52-4-224-221.compute-1.amazonaws.com/";

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public JSONObject getJSONFromUrl(String url, LinkedList<String> postNames,
                                     LinkedList<String> postVals, String key) {
        // Making HTTP request

        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            List<Cookie> cookies = httpClient.getCookieStore().getCookies();
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Authorization", "Bearer" + key);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                    postNames.size());
            ListIterator<String> listIterator = postNames.listIterator();
            while (listIterator.hasNext()) {
                nameValuePairs.add(new BasicNameValuePair(listIterator.next(),
                        postVals.get(listIterator.nextIndex() - 1)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            cookies = httpClient.getCookieStore().getCookies();

            // When HttpClient instance is no longer needed,
            // shut down the connection manager to ensure
            // immediate deallocation of all system resources
            // httpClient.getConnectionManager().shutdown();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            json = sb.toString();
            //Log.i("JSON Return", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
            return null;
        }
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
            return null;
        }
        // return JSON String
        return jObj;
    }

    public JSONObject getJSONFromUrl(String url){
        LinkedList<String> namePlaceholder = new LinkedList<String>();
        LinkedList<String> valsPlaceholder = new LinkedList<String>();
        String keyPlaceholder = "";
        return getJSONFromUrl(url, namePlaceholder, valsPlaceholder, keyPlaceholder);
    }

    public JSONObject getJSONFromUrlGET(String url, String key) {
        // Making HTTP request

        try {
            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Authorization", "Bearer" +key);
            HttpResponse execute = client.execute(httpGet);
            is = execute.getEntity().getContent();
            json = null;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "n");
            }
            is.close();
            json = sb.toString();
            //Log.i("JSON Return", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
            return null;
        }
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
            return null;
        }
        // return JSON String
        return jObj;
    }
}
