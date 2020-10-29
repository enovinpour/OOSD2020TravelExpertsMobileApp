package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.Executors;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.Customer;

import static org.ehsan.travelexpertsoosd.Validator.isValidEmailNoAlert;
import static org.ehsan.travelexpertsoosd.Validator.isValidPassword;


public class LoginActivity extends AppCompatActivity {

    SharedPreferences pref;
    Intent intent, registerIntent;
    View view;
    EditText txt_email, txt_password;
    ImageView img_login;
    Button btn_submit, btn_linkregister;
    TextView lbl_email, lbl_password;
    RequestQueue requestQueue;
    String email;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        intent = new Intent(LoginActivity.this, ProfileMainActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);

        img_login = findViewById(R.id.img_login);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        btn_submit = findViewById(R.id.btn_submit);
        btn_linkregister = findViewById(R.id.btn_linkregister);
        lbl_email = findViewById(R.id.lbl_email);
        lbl_password = findViewById(R.id.lbl_password);
        img_login.setImageResource(R.drawable.icon_sleepyowl);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = txt_email.getText().toString();
                password = txt_password.getText().toString();

                //
                if (email.isEmpty()) { //checks if email is empty
                    lbl_email.setText("Email * required field *");
                    lbl_email.setTextColor(Color.RED);
                } else if (!isValidEmailNoAlert(email)) { //checks if email is valid
                    lbl_email.setText("Email * invalid email *");
                    lbl_email.setTextColor(Color.RED);
                } else {
                    if (password.isEmpty()) { //checks if password is empty
                        lbl_password.setText("Password *required field *");
                        lbl_password.setTextColor(Color.RED);
                    } else if (!isValidPassword(password)) { //checks if password is valid
                        lbl_password.setText("Password * invalid password *");
                        lbl_password.setTextColor(Color.RED);
                    } else {
                        //else, email and password are valid
                        Executors.newSingleThreadExecutor().execute(new getCustomer(email));
                    }
                }
            }
        });

        btn_linkregister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    class getCustomer implements Runnable{
        private String validEmail;

        public getCustomer(String validEmail) {
            this.validEmail = validEmail;
        }

        @Override
        public void run() {
            //retrieve JSON data from REST service into StringBuffer
            StringBuffer buffer = new StringBuffer();
            //String url = "http:/192.168.0.17:8081/JSPDay4RESTJPAExample/rs/agent/getagent/" + packageId;
            String url = "http://192.168.133.1:8080/TravelExpertsOOSDJSP/rs/packagesalberta/LoginEmail/" + validEmail;
            Log.d("crystal", "Auth: " + url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    VolleyLog.wtf(response, "utf-8");
                    //convert JSON data from response string into an Agent
                    //create a json array to recieve the response- loop through it to make it into an object

                    String emailfromUrl = "";
                    String passwordfromUrl = "";
                    int idfromUrl = 0;

                    JSONObject pkg = null;
                    JSONArray customerResponse = null;
                    try {
                        customerResponse = new JSONArray(response);
                        if(customerResponse.isNull(0)){

                        } else {
                            pkg = customerResponse.getJSONObject(0);
                            idfromUrl = pkg.getInt("CustomerId");
                            emailfromUrl = pkg.getString("custEmail");
                            passwordfromUrl = pkg.getString("Password");
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                    //update ListView with the adapter of Agents
//                    final JSONObject finalPkg = pkg;
                        final String customerEmail = emailfromUrl;
                        final String customerPassword = passwordfromUrl;
                        final int customerid = idfromUrl;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                                String intstring = String.valueOf(customerid);
                                Log.d("crystalidtest", intstring);
                                Log.d("crystalemailtest" , customerEmail);
                                Log.d("crystalpasswordtest" , customerPassword);

                                if(email.equals(customerEmail)  && password.equals(customerPassword)){
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putInt("id", customerid);
                                    editor.commit();
                                    startActivity(intent);
                                    //redirect

                                    Log.d("successlogin", "login successfull");
                                } else {
                                    //failure case - some sort of message
                                    lbl_password.setText("Password * invalid password *");
                                    lbl_password.setTextColor(Color.RED);

                                    lbl_email.setText("Email * invalid email *");
                                    lbl_email.setTextColor(Color.RED);
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
}

