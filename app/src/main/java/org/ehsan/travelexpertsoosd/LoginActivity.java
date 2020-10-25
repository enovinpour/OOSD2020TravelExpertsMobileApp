package org.ehsan.travelexpertsoosd;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class LoginActivity extends AppCompatActivity {

    EditText txt_email, txt_password;
    ImageView img_login;
    Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View view;
        img_login = findViewById(R.id.img_login);

        img_login.setImageResource(R.drawable.icon_sleepyowl);
    }
}