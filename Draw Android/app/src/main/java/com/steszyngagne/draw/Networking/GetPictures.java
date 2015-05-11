package com.steszyngagne.draw.Networking;

import android.os.AsyncTask;

import com.steszyngagne.draw.Interfaces.PictureListRefreshable;
import com.steszyngagne.draw.Structs.ShapeListItem;
import com.steszyngagne.draw.Structs.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

import ca.qc.johnabbott.cs603.R;

public class GetPictures extends AsyncTask<Void, Integer, Void> {

    private WeakReference<User> logger;
    private Boolean connected;
    private PictureListRefreshable caller;

    private LinkedList<ShapeListItem> list = new LinkedList<>();

    private String LOGIN_URL = JSONParser.BASE_URL + "api/accounts/";

    public GetPictures(PictureListRefreshable caller) {
        this.caller = caller;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        // Post names and values
        String url = JSONParser.BASE_URL + "api/pictures";
        String key = Session.current.token;

        JSONParser parser = new JSONParser();
        JSONObject json = parser.getJSONFromUrlGET(url, key);

        if(json == null){
            return null;
        }

        connected = false;

        try{
            String status = json.getString("status");
            if(status.equals("success")){
                connected = true;

                try{
                    JSONArray pics = json.getJSONArray("response");

                    for(int i = 0; i < pics.length(); i++){

                        JSONObject curr = (JSONObject) pics.get(i);

                        String name = curr.getString("name");
                        String token = curr.getString("_id");

                        // Get the amount of shapes in a picture
                        // TODO
                        Integer numshapes = 0;

                        ShapeListItem sli = new ShapeListItem(name, token, numshapes);
                        list.add(sli);
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    caller = null;
                }
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
            caller.afterRefresh(list);
        }
    }
}
