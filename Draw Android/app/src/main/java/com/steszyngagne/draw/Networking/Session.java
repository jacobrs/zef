package com.steszyngagne.draw.Networking;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.steszyngagne.draw.Interfaces.BoolRefreshable;
import com.steszyngagne.draw.Structs.User;

public class Session implements BoolRefreshable{

    public static SharedPreferences prefs;
    public static User current;

    public static Activity currentActivity;

    private static BoolRefreshable rf;

    private static boolean login = false;

    public static Session GetInstance(){
        return new Session();
    }

    public static void Login(User user, BoolRefreshable rf){
        Session.current = user;

        //prefs.edit().putString("zef_username", user.username).apply();
        //prefs.edit().putString("zef_token", user.token).apply();

        // apply double login lock
        login = true;

        // set refreshable
        Session.rf = rf;

        new SendLogin(Session.GetInstance(), Session.current).execute();
    }


    @Override
    public void afterRefresh(boolean res, int code) {
        // remove lock
        login = false;
        Session.rf.afterRefresh(res, code);
    }

    public static void ShowShortError(String error){
        Toast.makeText(currentActivity, error, Toast.LENGTH_SHORT).show();
    }

    public static void ShowLongError(String error){
        Toast.makeText(currentActivity, error, Toast.LENGTH_LONG).show();
    }
}
