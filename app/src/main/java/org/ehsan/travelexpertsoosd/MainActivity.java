package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//       Intent intent = new Intent(getApplicationContext(), PackageSelectActivity.class);
//       Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
       Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        //lvPictures.setAdapter(adapter);
        startActivity(intent);

    }
}