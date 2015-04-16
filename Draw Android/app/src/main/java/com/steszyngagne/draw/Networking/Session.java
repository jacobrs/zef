package com.steszyngagne.draw.Networking;

import android.content.SharedPreferences;

import com.steszyngagne.draw.Structs.User;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class Session {

    public static SharedPreferences prefs;
    public static User current;

    public static boolean Login(User user){
        return false;
    }

}
