package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class ProfileEditActivity extends AppCompatActivity {
    Button btnSave;
    ImageButton btnEditBack;
    EditText txtUsername, txtUserEmail, txtUserPhone, txtUserAddress, txtUserDOB;
    RequestQueue requestQueue;
    Customer customer;
    int custId = 142;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        requestQueue = Volley.newRequestQueue(this);

        btnSave = findViewById(R.id.btnSave);
        btnEditBack = findViewById(R.id.btnEditBack);
        txtUsername = findViewById(R.id.txtUsername);
        txtUserEmail = findViewById(R.id.txtUserEmail);
        txtUserPhone = findViewById(R.id.txtUserPhone);
        txtUserAddress = findViewById(R.id.txtUserAddress);
        txtUserDOB = findViewById(R.id.txtUserDOB);

        Intent intent = getIntent();
        customer = (Customer) intent.getSerializableExtra("customer");

        Executors.newSingleThreadExecutor().execute(new GetCustomer(customer.getCustId()));

//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Customer customer = new Customer(
//                        Integer.parseInt(custId.toString()),
//                        etFirstName.getText().toString(),
//                        etMiddleInitial.getText().toString(),
//                        etLastName.getText().toString(),
//                        etBusPhone.getText().toString(),
//                        etEmail.getText().toString(),
//                        etPosition.getText().toString(),
//                        Integer.parseInt(etAgencyId.getText().toString()),
//                        etUserid.getText().toString(),
//                        etPassword.getText().toString()
//                );
//                Executors.newSingleThreadExecutor().execute(new PostCustomer(customer));
//            }
//        });
    }

    class GetCustomer implements Runnable {
        private int customerId;
//
        public GetCustomer(int customerId) {
            this.customerId = custId;
        }

        @Override
        public void run() {
            //retrieve JSON data from REST service into StringBuffer
            StringBuffer buffer = new StringBuffer();
            String url = "http://127.0.0.1:8080/TravelExpertsOOSDJSP2/rs/TEREST/getcustomer/" + custId;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    VolleyLog.wtf(response, "utf-8");

                    //convert JSON data from response string into an Customer
                    JSONObject cust = null;
                    try {
                        cust = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //update ListView with the adapter of Customers
                    final JSONObject finalCust = cust;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                txtUsername.setText(finalCust.getString("custFirstName" + "custLastName"));
                                txtUserEmail.setText(finalCust.getString("custEmail"));
                                txtUserPhone.setText(finalCust.getString("custBusPhone"));
                                txtUserAddress.setText(finalCust.getString("custAddress"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
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

//    class GetCustomers implements Runnable {
//        @Override
//        public void run() {
//            //retrieve JSON data from REST service into StringBuffer
//            StringBuffer buffer = new StringBuffer();
//            String url = "http://127.0.0.1:8080/TravelExpertsOOSDJSP2/rs/TEREST/getcustomers";
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    VolleyLog.wtf(response, "utf-8");
//
//                    //convert JSON data from response string into an ArrayAdapter of Customers
////                    Customer customer = new Customer();
//
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//                        for (int i=0; i<jsonArray.length(); i++)
//                        {
//                            JSONObject cust = jsonArray.getJSONObject(i);
//                            Customer c = new Customer(cust.getInt("CustomerId"), cust.getString("CustFirstName"),
//                                    cust.getString("CustLastName"), cust.getString(""), cust.getString(""), cust.getString(""), cust.getString(""),
//                                    cust.getString(""), cust.getString(""), cust.getString(""), cust.getString(""), cust.getInt(""), cust.getString(""));
//
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    //update ListView with the adapter of Customers
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

//    class PostCustomer implements Runnable {
//        private Customer customer;
//
//        public PostCustomer(Customer customer) {
//            this.customer = customer;
//        }
//
//        @Override
//        public void run() {
//            //send JSON data to REST service
//            String url = "http://192.168.0.23:8082/JSPDay4RESTJPAExample/rs/customer/postcustomer";
//            JSONObject obj = new JSONObject();
//            try {
//                obj.put("customerId", customer.getCustId() + "");
//                obj.put("custFirstName", customer.getCustFirstName() + "");
//                obj.put("custLastName", customer.getCustLastName() + "");
//                obj.put("custBusPhone", customer.getCustBusPhone() + "");
//                obj.put("custEmail", customer.getCustEmail() + "");
//                obj.put("password", customer.getPassword() + "");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(final JSONObject response) {
//                            Log.d("harv", "response=" + response);
//                            VolleyLog.wtf(response.toString(), "utf-8");
//
//                            //display result message
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.d("harv", "error=" + error);
//                            VolleyLog.wtf(error.getMessage(), "utf-8");
//                        }
//                    });
//
//            requestQueue.add(jsonObjectRequest);
//        }
//    }
}