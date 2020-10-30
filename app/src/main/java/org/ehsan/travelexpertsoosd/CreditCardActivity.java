package org.ehsan.travelexpertsoosd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

import Model.CreditCard;
import Model.Customer;

public class CreditCardActivity extends AppCompatActivity {
    TextView tvPrice;
    EditText etCardNumber, etCardName, etExpiry, etCode, etFirstName,
        etLastName, etAddress1, etAddress2, etPostal, etCity, etCountry, etPhone;
    Button btnPayment;
    RequestQueue requestQueue;
    Customer customer;
    SharedPreferences pref;
    int id;
    double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("Credit Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        price = intent.getDoubleExtra("Total",0);

        requestQueue = Volley.newRequestQueue(this);
        pref = getSharedPreferences("user_details",MODE_PRIVATE);

        id = pref.getInt("id", 1);

        tvPrice = findViewById(R.id.tvPrice);
        etCardNumber = findViewById(R.id.etCardNumber);
        etCardName = findViewById(R.id.etCardName);
        etExpiry = findViewById(R.id.etExpiry);
        etCode = findViewById(R.id.etCode);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAddress1 = findViewById(R.id.etAdress1);
        etAddress2 = findViewById(R.id.etAdress2);
        etPostal = findViewById(R.id.etPostal);
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        btnPayment = findViewById(R.id.btnPayment);
        etPhone = findViewById(R.id.etPhone);

        Executors.newSingleThreadExecutor().execute(new GetCustomer());

//        Executors.newSingleThreadExecutor().execute(new GetCredit());

        tvPrice.setText("$" + price);

        //Needs to be done tomorrow
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    class GetCustomer implements Runnable {
        @Override
        public void run() {
            StringBuffer buffer = new StringBuffer();
            String url = "http://192.168.0.32:8080/TravelExpertsOOSDJSP2/rs/TEREST/loginId/" + id;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    VolleyLog.wtf(response,"utf-8");

                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        JSONObject object = jsonArray.getJSONObject(0);

                        JSONObject object = new JSONObject(response);
                        Log.d("ehsan", "onResponse: " + object);

                        customer = new Customer(object.getInt("customerId"), object.getString("custFirstName"),
                                object.getString("custLastName"),object.getString("custAddress"), object.getString("custCity"),
                                object.getString("custProv"), object.getString("custPostal"), object.getString("custCountry"),
                                object.getString("custHomePhone"), object.getString("custBusPhone"), object.getString("custEmail"),
                                object.getInt("points"), object.getString("password"));

                        etFirstName.setText(customer.getCustFirstName());
                        etLastName.setText(customer.getCustLastName());
                        etAddress1.setText(customer.getCustAddress());
                        etPostal.setText(customer.getCustPostal());
                        etCity.setText(customer.getCustCity());
                        etCountry.setText(customer.getCustCountry());
                        etPhone.setText(customer.getCustBusPhone());


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


            // Credit Card

            buffer = new StringBuffer();
            url = "http://192.168.0.32:8080/TravelExpertsOOSDJSP2/rs/TEREST/getcc/" + id;
            stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    VolleyLog.wtf(response,"utf-8");

                    try{

                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject object = jsonArray.getJSONObject(0);
                        CreditCard cc = new CreditCard(object.getInt("creditCardId"),
                                object.getString("CCName"), object.getString("CCNumber"),
                                object.getString("CCExpiry"),object.getInt("customerId"));

                        etCardName.setText(customer.getCustFirstName() + " " + customer.getCustLastName());
                        etCardNumber.setText(cc.getCcNumber());
                        etExpiry.setText(cc.getCcExpiry());

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

//    class GetCredit implements Runnable {
//        @Override
//        public void run() {
//
//            StringBuffer buffer = new StringBuffer();
//            String url = "http://192.168.0.12:8081/OOSDTravelExperts/rs/agent/getcc/" + id;
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    VolleyLog.wtf(response,"utf-8");
//
//                    try{
//
//                        JSONArray jsonArray = new JSONArray(response);
//                        JSONObject object = jsonArray.getJSONObject(0);
//                        CreditCard cc = new CreditCard(object.getInt("creditCardId"),
//                                object.getString("CCName"), object.getString("CCNumber"),
//                                object.getString("CCExpiry"),object.getInt("customerId"));
//
//                        etCardName.setText(customer.getCustFirstName() + " " + customer.getCustLastName());
//                        etCardNumber.setText(cc.getCcNumber());
//                        etExpiry.setText(cc.getCcExpiry());
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    VolleyLog.wtf(error.getMessage(), "utf-8");
//                }
//            });
//
//            requestQueue.add(stringRequest);
//        }
//    }
}