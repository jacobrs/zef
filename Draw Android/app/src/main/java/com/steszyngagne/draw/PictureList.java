package com.steszyngagne.draw;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.steszyngagne.draw.Adapters.PicturesAdapter;
import com.steszyngagne.draw.Interfaces.PictureListRefreshable;
import com.steszyngagne.draw.Networking.GetPictures;
import com.steszyngagne.draw.Networking.Session;
import com.steszyngagne.draw.Structs.ShapeListItem;

import java.util.LinkedList;

import ca.qc.johnabbott.cs603.R;


public class PictureList extends Activity implements PictureListRefreshable{

    private PicturesAdapter pa;
    private ListView lv;

    public SwipeRefreshLayout swipeRefresh;

    public final PictureList container = this;

    private View loadingLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_list);

        Session.currentActivity = this;

        new GetPictures(this).execute();

        lv = (ListView) findViewById(android.R.id.list);
        loadingLayer = findViewById(R.id.loading_layer);

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiper_refresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.teal));

        swipeRefresh.setEnabled(false);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pa = new PicturesAdapter(getApplication(), new LinkedList<ShapeListItem>());
                lv.setAdapter(pa);

                new GetPictures(container).execute();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void afterRefresh(LinkedList<ShapeListItem> list) {
        if(loadingLayer != null){
            loadingLayer.setVisibility(View.GONE);
        }
        if(lv != null){
            pa = new PicturesAdapter(getApplication(), list);
            lv.setAdapter(pa);
        }
        swipeRefresh.setRefreshing(false);
        swipeRefresh.setEnabled(true);
    }
}
