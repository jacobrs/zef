package com.steszyngagne.draw.Networking;

import android.os.AsyncTask;

import com.steszyngagne.draw.Interfaces.BoolRefreshable;
import com.steszyngagne.draw.Login;
import com.steszyngagne.draw.Structs.User;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

import ca.qc.johnabbott.cs603.R;

public class SendLogin extends AsyncTask<Void, Integer, Void> {

    private WeakReference<User> logger;
    private Boolean connected;
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

        names.add("username");
        vals.add(logger.get().username);
        names.add("password");
        vals.add(logger.get().email);

        JSONParser parser = new JSONParser();
        JSONObject json = parser.getJSONFromUrl(LOGIN_URL, names, vals, ""); // No need for a key here

        if(json == null){
            return null;
        }

        connected = false;

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
        if(connected == null) {
            Session.ShowShortError(Session.currentActivity.getString(R.string.conn_error));
        }else if(caller != null){
            caller.afterRefresh(connected, Login.LOGIN);
        }
    }
}
