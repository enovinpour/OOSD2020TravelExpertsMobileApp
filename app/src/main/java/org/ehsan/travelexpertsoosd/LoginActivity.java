package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Model.Customer;

import static org.ehsan.travelexpertsoosd.Validator.isValidEmailNoAlert;
import static org.ehsan.travelexpertsoosd.Validator.isValidPassword;


public class LoginActivity extends AppCompatActivity {

    View view;
    EditText txt_email, txt_password;
    ImageView img_login;
    Button btn_submit;
    TextView lbl_email, lbl_password;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);

        img_login = findViewById(R.id.img_login);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        btn_submit = findViewById(R.id.btn_submit);
        lbl_email = findViewById(R.id.lbl_email);
        lbl_password = findViewById(R.id.lbl_password);

        img_login.setImageResource(R.drawable.icon_sleepyowl);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String email = txt_email.getText().toString();
                    String password = txt_password.getText().toString();
                    if (email.isEmpty()){ //checks if email is empty
                            lbl_email.setText("Email * required field *");
                            lbl_email.setTextColor(Color.RED);
                    } else if (!isValidEmailNoAlert(email)) { //checks if email is valid
                        lbl_email.setText("Email * invalid email *");
                        lbl_email.setTextColor(Color.RED);
                    } else {
                        String validEmail = email;  //else, email is valid - pass it to new validEmail variable
                    }

                    if (password.isEmpty()){ //checks if password is empty
                            lbl_password.setText("Password *required field *");
                            lbl_password.setTextColor(Color.RED);
                    } else if (!isValidPassword(password)){ //checks if password is valid
                        lbl_password.setText("Password * invalid password *");
                        lbl_password.setTextColor(Color.RED);
                    } else {  //this should only operate if the previous 2 if statements return true? (QUESTION THAT? )
                        String validPassword = password; // else, password is valid - pass it to validPassword variable
                    }

                    // so now we know we have a valid password and email, next step is to check that with the DB
                            // some sort of magic to connect with JPA?


                    // if password and email exist together in the db - login is successfull and redirects user to the main page
                    // sql query   (  SELECT * FROM `customers` WHERE `CustEmail` = 'testcustomer@email.com' AND `Password` = 'Password#1'  )
                    // where , the custEmail = validEmail and password = validPassword
            }

        });
        class PostAgent implements Runnable {
            private Customer customer;

            public PostAgent(Customer customer) {
                this.customer = customer;
            }

            @Override
            public void run() {
                //send JSON data to REST service
                String url = "http://10.0.0.1:8080/TravelExpertsOOSDJSP/rs/packagesalberta/logincustomer";
                JSONObject obj = new JSONObject();
                try {
                    obj.put("CustEmail", customer.getCustEmail() + "");
                    obj.put("Password", customer.getPassword() + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(final JSONObject response) {
                                Log.d("harv", "response=" + response);
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
                                Log.d("crystal", "error=" + error);
                                VolleyLog.wtf(error.getMessage(), "utf-8");
                            }
                        });

                requestQueue.add(jsonObjectRequest);
            }
        }
}

}
