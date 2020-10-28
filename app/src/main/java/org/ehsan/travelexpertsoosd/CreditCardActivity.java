package org.ehsan.travelexpertsoosd;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("Credit Card");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        requestQueue = Volley.newRequestQueue(this);

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

        int Price = 500;
        customer = new Customer(1, "Ehsan","Novin","102 Wakanda"
        ,"Calgary","AB","T2T-1Z0","Country","40311123332"
        ,"40311332211","enovin@test.com",100,"password");

        Executors.newSingleThreadExecutor().execute(new GetCredit());

        tvPrice.setText("$" + Price);
        etFirstName.setText(customer.getCustFirstName());
        etLastName.setText(customer.getCustLastName());
        etAddress1.setText(customer.getCustAddress());
        etPostal.setText(customer.getCustPostal());
        etCity.setText(customer.getCustCity());
        etCountry.setText(customer.getCustCountry());
        etPhone.setText(customer.getCustBusPhone());


    }

    class GetCredit implements Runnable {
        @Override
        public void run() {

            StringBuffer buffer = new StringBuffer();
            String url = "http://192.168.0.12:8081/JSPDay4JPA2/rs/agent/getcc/123";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
}