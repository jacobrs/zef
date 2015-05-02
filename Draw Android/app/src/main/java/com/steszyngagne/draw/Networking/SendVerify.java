package com.steszyngagne.draw.Networking;

import android.os.AsyncTask;

import com.steszyngagne.draw.Interfaces.BoolRefreshable;
import com.steszyngagne.draw.Login;
import com.steszyngagne.draw.Structs.User;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

import ca.qc.johnabbott.cs603.R;

public class SendVerify extends AsyncTask<Void, Integer, Void> {

    private Boolean connected;
    private BoolRefreshable caller;

    private String LOGIN_URL = JSONParser.BASE_URL + "api/accounts/";

    public SendVerify(BoolRefreshable caller) {
        this.caller = caller;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Post names and values
        LinkedList<String> names = new LinkedList<>();
        LinkedList<String> vals = new LinkedList<>();

        names.add("apikey");
        vals.add(Session.prefs.getString("zef_token", ""));

        JSONParser parser = new JSONParser();
        JSONObject json = parser.getJSONFromUrl(LOGIN_URL, names, vals);

        if(json == null){
            return null;
        }

        connected = false;

        try{
            String status = json.getString("status");
            if(status.equals("success")){
                connected = true;
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
