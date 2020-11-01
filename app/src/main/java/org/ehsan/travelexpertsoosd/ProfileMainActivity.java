package org.ehsan.travelexpertsoosd;

/*
Author: Jessy Perreault
Class Name: PROJ-207-OOS
Date: November 2020
 */

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

import Model.Customer;

public class ProfileMainActivity extends AppCompatActivity {
    SharedPreferences prf;
    Button btnEditProfile, btnLogout, btnExplore;
    RequestQueue requestQueue;
    Customer customer;
    Intent intentEdit, intentLogout, intentExplore;
    int custId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_main);
        intentEdit = new Intent(ProfileMainActivity.this, ProfileEditActivity.class);
        intentLogout = new Intent(ProfileMainActivity.this, LoginActivity.class);
        intentExplore = new Intent(ProfileMainActivity.this, PackageSelectActivity.class);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        custId = prf.getInt("id", 1);

        requestQueue = Volley.newRequestQueue(this);

        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLogout = findViewById(R.id.btnLogout);
        btnExplore = findViewById(R.id.btnExplore);

        Intent intent = getIntent();
        customer = (Customer) intent.getSerializableExtra("customer");

        Executors.newSingleThreadExecutor().execute(new ProfileMainActivity.GetCustomer());

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(intentEdit);
            }
        });

        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentExplore);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = prf.edit();
                editor.clear();
                editor.apply();
                startActivity(intentLogout);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        customer = (Customer) intent.getSerializableExtra("customer");
        Executors.newSingleThreadExecutor().execute(new ProfileMainActivity.GetCustomer());
    }

    class GetCustomer implements Runnable {
        @Override
        public void run() {
            //retrieve JSON data from REST service into StringBuffer
            StringBuffer buffer = new StringBuffer();
            String url = "http://192.168.0.32:8080/TravelExpertsOOSDJSP2/rs/TEREST/getcustomerlist/" + custId;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    VolleyLog.wtf(response, "utf-8");

                    //convert JSON data from response string into an Customer

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject object = jsonArray.getJSONObject(0);
                        Customer cust = new Customer(object.getInt("CustomerId"), object.getString("CustFirstName"),
                                object.getString("CustLastName"));
                        btnEditProfile.setText(cust.getCustFirstName() + " " + cust.getCustLastName());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.wtf(error.getMessage(), "utf-8");
                }
            });

            requestQueue.add(stringRequest);
        }
    }
}