package com.steszyngagne.draw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.qc.johnabbott.cs603.R;

public class Login extends Activity {

    View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        root = getWindow().getDecorView();

        // Set listener on button
        root.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch login
                login();
            }
        });

        root.findViewById(R.id.password).setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    login();
                }
                return false;
            }
        });

    }

    private void login(){
        String username = ((EditText) root.findViewById(R.id.username)).getText().toString();
        String password = ((EditText) root.findViewById(R.id.password)).getText().toString();

        // validate the username and password fields aren't empty
        if(username.trim().equals("") || password.trim().equals("")){
            Toast.makeText(getApplicationContext(), getString(R.string.missing), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent home = new Intent(Login.this, MainActivity.class);
        startActivity(home);
    }
}
