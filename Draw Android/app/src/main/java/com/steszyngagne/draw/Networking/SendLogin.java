package com.steszyngagne.draw.Networking;

import android.os.AsyncTask;

import com.steszyngagne.draw.Interfaces.BoolRefreshable;
import com.steszyngagne.draw.Structs.User;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class SendLogin extends AsyncTask<Void, Integer, Void> {

    private WeakReference<User> logger;
    private Boolean connected = false;
    private BoolRefreshable caller;

    private String LOGIN_URL = JSONParser.BASE_URL + "api/accounts/";

    public SendLogin(BoolRefreshable caller, User logger) {
        this.caller = caller;
        this.logger = new WeakReference<>(logger);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Post names and values
        LinkedList<String> names = new LinkedList<>();
        LinkedList<String> vals = new LinkedList<>();

        JSONParser parser = new JSONParser();
        JSONObject json = parser.getJSONFromUrl(LOGIN_URL, names, vals);

        try{
            String status = json.getString("status");
            if(status.equals("success")){
                connected = true;
                logger.get().token = json.getString("token");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void success){
        if(caller != null){
            caller.afterRefresh(connected);
        }
    }
}
