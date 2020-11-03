package org.ehsan.travelexpertsoosd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


///AUTHOR : Crystal Champion ///
public class LandingActivity extends AppCompatActivity {
    View view;
    ConstraintLayout id_layout;
    ImageView img_landing, img_logo;
    Button btn_startnow;
    TextView lbl_slogan;
Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        id_layout = findViewById(R.id.id_layout);
        img_landing = findViewById(R.id.img_landing);
        img_logo = findViewById(R.id.img_logo);
        lbl_slogan = findViewById(R.id.lbl_slogan);
        btn_startnow = findViewById(R.id.btn_startnow);
        intent = new Intent(LandingActivity.this, LoginActivity.class);
        img_landing.setImageResource(R.drawable.hikerlanding);
        img_logo.setImageResource(R.drawable.expertlogo);

            //click 'start now' -> load login
        btn_startnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
        }



