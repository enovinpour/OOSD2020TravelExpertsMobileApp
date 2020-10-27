package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

//       Intent intent = new Intent(getApplicationContext(), PackageSelectActivity.class);
       Intent intent = new Intent(getApplicationContext(), ProfileEditActivity.class);
        //lvPictures.setAdapter(adapter);
        startActivity(intent);

    }
}