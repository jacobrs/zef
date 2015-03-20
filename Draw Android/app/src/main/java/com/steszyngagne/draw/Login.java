package com.steszyngagne.draw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ca.qc.johnabbott.cs603.R;

public class Login extends Activity {

    View root;

    private boolean showingRegister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        root = getWindow().getDecorView();

        hideRegister();

        TextView need = (TextView) root.findViewById(R.id.need);
        need.setText(Html.fromHtml(getString(R.string.need_signup)));

        need.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showingRegister){
                    hideRegister();
                    showingRegister = false;
                    return;
                }
                showRegister();
                showingRegister = true;
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

    private void register(){

    }

    private void showRegister(){
        EditText username = (EditText) root.findViewById(R.id.username);
        EditText reg_email = (EditText) root.findViewById(R.id.reg_email);
        reg_email.setVisibility(View.VISIBLE);

        username.setHint(getString(R.string.username));
        ((Button) root.findViewById(R.id.submit)).setText(getString(R.string.signup));
        TextView need = (TextView) root.findViewById(R.id.need);
        need.setText(Html.fromHtml(getString(R.string.back_signin)));

        // Set listener on button
        root.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch login
                register();
            }
        });

        reg_email.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    register();
                }
                return false;
            }
        });

        root.findViewById(R.id.password).setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // Do nothing, this is not the last field anymore
                return false;
            }
        });
    }

    private void hideRegister(){
        EditText username = (EditText) root.findViewById(R.id.username);
        EditText reg_email = (EditText) root.findViewById(R.id.reg_email);
        reg_email.setVisibility(View.GONE);

        username.setHint(getString(R.string.username_email));
        ((Button) root.findViewById(R.id.submit)).setText(getString(R.string.signin));
        TextView need = (TextView) root.findViewById(R.id.need);
        need.setText(Html.fromHtml(getString(R.string.need_signup)));

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
}
