package org.ehsan.travelexpertsoosd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.concurrent.Executors;

import Model.Booking;
import Model.BookingDetail;
import Model.CreditCard;
import Model.Customer;
import Model.Package;

/*
Author: Ehsan Novin-Pour
 */

public class CreditCardActivity extends AppCompatActivity {
    //Global Variable
    private TextView tvPrice;
    private EditText etCardNumber, etCardName, etExpiry, etCode, etFirstName,
        etLastName, etAddress1, etEmail, etPostal, etCity, etCountry, etPhone;
    private Button btnPayment;
    private RequestQueue requestQueue;
    private Customer customer;
    private SharedPreferences pref;
    private int id, qty, packageId;
    private double price;
    private String custFirstName, custLastName, custCardHolderName, custCardNumber, expiryDate
        ,security, custEmail, custAddress, custCity, custPostal, custCountry, custPhone, expiryDateFormat;
    private Booking booking;
    private BookingDetail bd;
    private Package checkoutPackage;
    private Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("Credit Card");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Grab data from passed over from checkout activity
        qty = 1;
        Intent intent = getIntent();
        price = intent.getDoubleExtra("Total",0);
        qty = intent.getIntExtra("qty",1);
        Package checkoutPackage = (Package) intent.getSerializableExtra("passedObject");
        packageId = checkoutPackage.getPackageId();


        requestQueue = Volley.newRequestQueue(this);
        //Get sharedprefences set in login activity
        pref = getSharedPreferences("user_details",MODE_PRIVATE);


        tvPrice = findViewById(R.id.tvPrice);
        etCardNumber = findViewById(R.id.etCardNumber);
        etCardName = findViewById(R.id.etCardName);
        etExpiry = findViewById(R.id.etExpiry);
        etCode = findViewById(R.id.etCode);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etAddress1 = findViewById(R.id.etAdress1);
        etPostal = findViewById(R.id.etPostal);
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        btnPayment = findViewById(R.id.btnPayment);
        etPhone = findViewById(R.id.etPhone);

        //id comes from login activity
        id = pref.getInt("id", 1);
        // execute the thread
        Executors.newSingleThreadExecutor().execute(new GetCustomer());

//        Executors.newSingleThreadExecutor().execute(new GetCredit());


        tvPrice.setText("$" + price);

        java.util.Date utilDate = new java.util.Date();
        date = new java.sql.Date(utilDate.getTime());
        Log.d("Ehsan", "onCreate: " + date);


        //Payment button event handler
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custCardHolderName = etCardName.getText().toString();
                custCardNumber = etCardNumber.getText().toString();
                expiryDate = etExpiry.getText().toString();
                security = etCode.getText().toString();
                custFirstName = etFirstName.getText().toString();
                custLastName = etLastName.getText().toString();
                custEmail = etEmail.getText().toString();
                custPostal = etPostal.getText().toString();
                custCountry = etCountry.getText().toString();
                custPhone = etPhone.getText().toString();

                //if all fields are filled execute the PutBooking thread
                if (!custCardNumber.isEmpty() && !custCardNumber.isEmpty() && !expiryDate.isEmpty()
                        && !security.isEmpty() && !custFirstName.isEmpty() && !custLastName.isEmpty() &&
                        !custEmail.isEmpty() && !custPostal.isEmpty() && !custCountry.isEmpty() &&
                        !custPhone.isEmpty()) {
                    Executors.newSingleThreadExecutor().execute(new PutBooking());

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please Fill Out All Fields Correctly", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    //GetCustomer runnable class
    class GetCustomer implements Runnable {
        @Override
        public void run() {
            StringBuffer buffer = new StringBuffer();
            String url = "http://192.168.0.12:8081/OOSDTravelExperts/rs/travel/loginId/" + id;
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
                        etEmail.setText(customer.getCustEmail());
                        etPostal.setText(customer.getCustPostal());
                        etCity.setText(customer.getCustCity());
                        etCountry.setText(customer.getCustCountry());
                        etPhone.setText(customer.getCustBusPhone());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.wtf(error.getMessage(), "utf-8");
                }
            });

            requestQueue.add(stringRequest);


            //To allow the app to have enough time to parse the first JSON Object. This solved the
            //errors that we were getting which were suspected to be due to timing.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Credit Card

            buffer = new StringBuffer();
            url = "http://192.168.0.12:8081/OOSDTravelExperts/rs/travel/getcc/" + id;
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

                        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");

                        try {
                            java.util.Date exipryDateUnformat = format.parse(cc.getCcExpiry());
                            format.applyPattern("MM/yy");
                            expiryDateFormat = format.format(exipryDateUnformat);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        etExpiry.setText(expiryDateFormat);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.wtf(error.getMessage(), "utf-8");
                }
            });

            requestQueue.add(stringRequest);

        }
    }


    //PutBooking runnable Class
    class PutBooking implements Runnable {
        private Booking booking;

        @Override
        public void run() {
            String url = "http://192.168.0.12:8081/OOSDTravelExperts/rs/travel/putbooking";
            JSONObject obj = new JSONObject();
            try {
                obj.put("bookingDate", date);
                obj.put("bookingNo", "12345");
                obj.put("travelerCount", qty);
                obj.put("customerId", customer.getCustId());
                obj.put("tripTypeId", "L");
                obj.put("packageId", packageId);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d("Ehsan", "response" + response);
                            VolleyLog.wtf(response.toString(), "utf-8");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        //If put is successful, make toast and switch over to the profileMainActivity
                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(getApplicationContext(), ProfileMainActivity.class);
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    },
                        new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Ehsan", "error=" + error);
                            VolleyLog.wtf(error.getMessage(), "utf-8");
                        }
                    });
                requestQueue.add(jsonObjectRequest);
        }
    }
}
