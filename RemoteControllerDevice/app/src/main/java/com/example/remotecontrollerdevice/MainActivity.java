package com.example.remotecontrollerdevice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final RemoteControllerService remoteController = new RemoteControllerService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setUrl(sharedPreferences.getString("urlTarget", ""));
        sharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences1, s) -> setUrl(sharedPreferences1.getString("urlTarget", "")));

        findViewById(R.id.checkBoxDrag).setOnClickListener(this::checkBoxDragClickListener);
        findViewById(R.id.buttonSettings).setOnClickListener(this::settingsEventListener);
        findViewById(R.id.buttonLeftClick).setOnClickListener(this::leftClickButtonEventListener);
        findViewById(R.id.buttonRightClick).setOnClickListener(this::rightClickButtonEventListener);
        findViewById(R.id.mockView2).setOnTouchListener(this::motionEventHandler);
        findViewById(R.id.mockView2).setOnClickListener(this::leftClickButtonEventListener);
        ((EditText)findViewById(R.id.editTextSend)).addTextChangedListener(createTextWatcher());
        ((EditText)findViewById(R.id.editTextSend)).setKeyListener(createKeyListener());
    }

    private KeyListener createKeyListener() {
        return new KeyListener() {
            @Override
            public int getInputType() {
                return 0;
            }
            @Override
            public boolean onKeyDown(View view, Editable editable, int i, KeyEvent keyEvent) {
                return false;
            }
            @Override
            public boolean onKeyUp(View view, Editable editable, int i, KeyEvent keyEvent) {
                remoteController.sendKeyTyped(keyEvent.getKeyCode());
                return true;
            }
            @Override
            public boolean onKeyOther(View view, Editable editable, KeyEvent keyEvent) {
                return false;
            }
            @Override
            public void clearMetaKeyState(View view, Editable editable, int i) {

            }
        };
    }

    private TextWatcher createTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.length() > 0) {
                    char firstChar = s.charAt(0);
                    remoteController.sendKeyTyped(firstChar);
                    ((EditText)findViewById(R.id.editTextSend)).setText("");
                }
            }
        };
    }

    private void checkBoxDragClickListener(View view) {
        CheckBox checkBox = (CheckBox)view;
        boolean checked = checkBox.isChecked();
        if (checked) {
            remoteController.sendMousePressed(0);
        } else {
            remoteController.sendMouseReleased(0);
        }
    }

    private void settingsEventListener(View view) {
        Log.d("ButtonEvent", "Running settings.");
        Intent i = new Intent(MainActivity.this, SettingsActivity2.class);
        startActivity(i);
    }

    private void leftClickButtonEventListener(View view) {
        remoteController.sendMouseClicked(0);
    }

    private void rightClickButtonEventListener(View view) {
        remoteController.sendMouseClicked(2);
    }

    private void setUrl(String url) {
        if (url != null && url.contains(":")) {
            String address = url.split(":")[0];
            String port = url.split(":")[1];
            remoteController.connect(address, port);
        }
    }

    private boolean motionEventHandler(View view, MotionEvent motionEvent) {
        boolean isDown = motionEvent.getAction() == MotionEvent.ACTION_DOWN;

        if (isDown) {
            remoteController.init();
        }

        float x = motionEvent.getAxisValue(MotionEvent.AXIS_X);
        float y = motionEvent.getAxisValue(MotionEvent.AXIS_Y);

        remoteController.setMovePoint(x, y);

        return true;
    }
}