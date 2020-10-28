package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import Model.Package;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Intent intent = new Intent(getApplicationContext(), PackageSelectActivity.class);
//      Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//     Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
     //Intent intent = new Intent(getApplicationContext(), CreditCardActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionsmenu, menu );
        return super.onCreateOptionsMenu(menu);
    }

}