package org.ehsan.travelexpertsoosd;

/*
Author: Jessy Perreault
Class Name: PROJ-207-OOS
Date: November 2020
 */

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

import Model.Customer;

import static org.ehsan.travelexpertsoosd.Validator.isValidEmailNoAlert;
import static org.ehsan.travelexpertsoosd.Validator.isValidPassword;

public class ProfileEditActivity extends AppCompatActivity {
    Button btnSave;
    ImageButton btnEditBack;
    TextView txtValidator, lblFirstName, lblLastName, lblEmail, lblPhone, lblAddress;
    EditText txtFirstName, txtLastName, txtUserEmail, txtUserPhone, txtUserAddress;
    RequestQueue requestQueue;
    SharedPreferences prf;
    Customer customer;
    int agentId = 1;
    int custId;
    String custCity = "";
    String custProv = "";
    String custPostal = "";
    String custCountry = "";
    String custHomePhone = "";
    String custPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        custId = prf.getInt("id", 1);

        requestQueue = Volley.newRequestQueue(this);

        btnSave = findViewById(R.id.btnSave);
        btnEditBack = findViewById(R.id.btnEditBack);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        txtUserEmail = findViewById(R.id.txtUserEmail);
        txtUserPhone = findViewById(R.id.txtUserPhone);
        txtUserAddress = findViewById(R.id.txtUserAddress);
        txtValidator = findViewById(R.id.txtValidator);
        lblFirstName = findViewById(R.id.lblFirstName);
        lblLastName = findViewById(R.id.lblLastName);
        lblPhone = findViewById(R.id.lblPhone);
        lblAddress = findViewById(R.id.lblAddress);
        lblEmail = findViewById(R.id.lblEmail);
        btnEditBack = findViewById(R.id.btnEditBack);
        btnEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        customer = (Customer) intent.getSerializableExtra("customer");

        Executors.newSingleThreadExecutor().execute(new GetCustomer());



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = txtFirstName.getText().toString();
                String lastName = txtLastName.getText().toString();
                String address = txtUserAddress.getText().toString();
                String city = custCity;
                String prov = custProv;
                String postal = custPostal;
                String country = custCountry;
                String homePhone = custHomePhone;
                String phone = txtUserPhone.getText().toString();
                String email = txtUserEmail.getText().toString();
                Customer customer = new Customer(
                        custId, firstName, lastName, address, city, prov, postal, country, homePhone, phone, email, agentId, custPassword
                );
                if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) { //checks if email is empty
                    txtValidator.setText("Please fill in all required fields *");
                } else if (!isValidEmailNoAlert(email)) { //checks if email is valid
                    lblEmail.setText("Email * invalid email *");
                    lblEmail.setTextColor(Color.RED);
                } else
                {
                    //else, all fields are valid
                    Executors.newSingleThreadExecutor().execute(new PostCustomer(customer));
                }

            }
        });
    }

    class GetCustomer implements Runnable {
        @Override
        public void run() {
            //retrieve JSON data from REST service into StringBuffer
            StringBuffer buffer = new StringBuffer();
            String url = "http://192.168.0.12:8081/OOSDTravelExperts/rs/travel/getcustomerlist/" + custId;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    VolleyLog.wtf(response, "utf-8");

                    //convert JSON data from response string into an Customer

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject object = jsonArray.getJSONObject(0);
                        Customer cust = new Customer(
                                    object.getInt("CustomerId"), object.getString("CustFirstName"), object.getString("CustLastName"),
                                    object.getString("CustAddress"), object.getString("CustCity"), object.getString("CustProv"),
                                    object.getString("CustPostal"), object.getString("CustCountry"), object.getString("CustHomePhone"),
                                    object.getString("CustBusPhone"), object.getString("CustEmail"), object.getInt("AgentId"), object.getString("Password"));
                        custId = cust.getCustId();
                        txtFirstName.setText(cust.getCustFirstName());
                        txtLastName.setText(cust.getCustLastName());
                        txtUserAddress.setText(cust.getCustAddress());
                        custCity = cust.getCustCity();
                        custProv = cust.getCustProv();
                        custPostal = cust.getCustPostal();
                        custCountry = cust.getCustCountry();
                        custHomePhone = cust.getCustHomePhone();
                        txtUserPhone.setText(cust.getCustBusPhone());
                        txtUserEmail.setText(cust.getCustEmail());
                        agentId = cust.getAgentId();
                        custPassword = cust.getPassword();

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

    class PostCustomer implements Runnable {
        private Customer customer;
        public PostCustomer(Customer customer) {
            this.customer = customer;
        }
        @Override
        public void run() {
            //send JSON data to REST service
            String url = "http://192.168.0.12:8081/OOSDTravelExperts/rs/travel/postcustomer/";
            JSONObject obj = new JSONObject();
            try {
                obj.put("customerId", customer.getCustId());
                obj.put("custFirstName", customer.getCustFirstName()+ "");
                obj.put("custLastName", customer.getCustLastName() + "");
                obj.put("custAddress", customer.getCustAddress() + "");
                obj.put("custCity", customer.getCustCity() + "");
                obj.put("custProv", customer.getCustProv() + "");
                obj.put("custPostal", customer.getCustPostal() + "");
                obj.put("custCountry", customer.getCustCountry() + "");
                obj.put("custHomePhone", customer.getCustHomePhone() + "");
                obj.put("custBusPhone", customer.getCustBusPhone() + "");
                obj.put("custEmail", customer.getCustEmail() + "");
                obj.put("agentId", customer.getAgentId());
                obj.put("password", customer.getPassword());


            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d("crystal", "response=" + response);
                            VolleyLog.wtf(response.toString(), "utf-8");
                            //display result message
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("harv", "error=" + error);
                            VolleyLog.wtf(error.getMessage(), "utf-8");
                        }
                    });
            requestQueue.add(jsonObjectRequest);
            Intent intentProfileMain = new Intent(ProfileEditActivity.this, ProfileMainActivity.class);
            finish();
            startActivity(intentProfileMain);
        }
    }
}