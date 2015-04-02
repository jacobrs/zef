package com.steszyngagne.draw;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

import ca.qc.johnabbott.cs603.R;

public class MainActivity extends Activity {

    private DrawingView drawing;
    private Dialog current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawing = (DrawingView)this.findViewById(R.id.drawing_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.menu_tools:
                showToolsDialog();
                return true;
            case R.id.menu_menu:
                showMenuDialog();
                return true;
            case R.id.signout:
                signOut();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showToolsDialog() {
        new ToolSettingsDialog(this, drawing.getToolBox());
    }

    private void showMenuDialog() {
        current = new Dialog(this);
        current.setContentView(R.layout.dialog_menu);
        current.setTitle("Menu");
        current.setCanceledOnTouchOutside(true);

        Button buttonErase = (Button) current.findViewById(R.id.buttonErase);
        Button buttonUndo = (Button) current.findViewById(R.id.buttonUndo);

        buttonErase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    Log.d("Shapes", drawing.getPicture().shapesToJson().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                drawing.erase();
                drawing.invalidate();
                current.dismiss();
            }
        });

        buttonUndo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!drawing.getPicture().getShapes().isEmpty()) {
                    drawing.undo();
                    drawing.invalidate();
                    current.dismiss();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Nothing to Undo", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        current.show();
    }

    private void signOut(){
        Intent home = new Intent(MainActivity.this, Login.class);
        startActivity(home);
        this.finish();
    }

}
