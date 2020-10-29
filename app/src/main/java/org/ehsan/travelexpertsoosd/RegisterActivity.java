package org.ehsan.travelexpertsoosd;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

import Model.Customer;

import static org.ehsan.travelexpertsoosd.Validator.isValidEmailNoAlert;
import static org.ehsan.travelexpertsoosd.Validator.isValidPassword;

public class RegisterActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    View view;
    EditText txt_firstName, txt_lastName, txt_initial, txt_custPhone, txt_email, txt_password;
    Button btn_submit;
    TextView lbl_email, lbl_password, lbl_firstName, lbl_lastName, lbl_initial, lbl_phoneNumber;
    Intent toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        requestQueue = Volley.newRequestQueue(this);
        toLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        txt_firstName = findViewById(R.id.txt_firstName);
        txt_initial = findViewById(R.id.txt_initial);
        txt_lastName = findViewById(R.id.txt_lastName);
        txt_custPhone = findViewById(R.id.txt_custPhone);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);

        lbl_firstName = findViewById(R.id.lbl_firstName);
        lbl_initial = findViewById(R.id.lbl_initial);
        lbl_lastName = findViewById(R.id.lbl_lastName);
        lbl_phoneNumber = findViewById(R.id.lbl_phoneNumber);
        lbl_email = findViewById(R.id.lbl_email);
        lbl_password = findViewById(R.id.lbl_password);

        btn_submit = findViewById(R.id.btn_submit);


        btn_submit.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              String firstName = txt_firstName.getText().toString();
                                              String lastName = txt_lastName.getText().toString();
                                              String phone = txt_custPhone.getText().toString();
                                              String email = txt_email.getText().toString();
                                              String password = txt_password.getText().toString();

                                              if (!EmailValidation(email) || !PhoneNumberValidation(phone) || !FirstNameValidation(firstName) || !LastNameValidation(lastName) || !PasswordValidation(password)) {

                                                  if (!EmailValidation(email)) {
                                                      EmailValidation(email);
                                                  }

                                                  if (!PhoneNumberValidation(phone)) {
                                                      PhoneNumberValidation(phone);
                                                  }

                                                  if (!FirstNameValidation(firstName)) {
                                                      FirstNameValidation(firstName);

                                                  }
                                                  if (!LastNameValidation(lastName)) {
                                                      LastNameValidation(lastName);
                                                  }

                                                  if (!PasswordValidation(password)) {
                                                      PasswordValidation(password);
                                                  }
                                              } else {

                                                  {
                                                  Customer customer = new Customer(
                                                          firstName,
                                                          lastName,
                                                          phone,
                                                          email,
                                                          password
                                                  );
                                                  Log.d("customer crystal ", customer.getCustFirstName());

                                                  Executors.newSingleThreadExecutor().execute(new RegisterActivity.PutCustomer(customer));
                                                      startActivity(toLogin);

                                                  }
                                              }
                                          }
        });
    }

    public boolean EmailValidation(String email){
        if (email.isEmpty()) { //checks if email is empty                          //checks email
            lbl_email.setText("Email *required field");
            lbl_email.setTextColor(Color.RED);
            return false;
        } else if (!isValidEmailNoAlert(email)) { //checks if email is valid        /
            lbl_email.setText("Email * invalid email *");
            lbl_email.setTextColor(Color.RED);
            return false;
        } else {
            lbl_email.setText("Email");
            lbl_email.setTextColor(Color.BLACK);
            return true;
        }
    }

    public boolean PasswordValidation(String password){
        if (password.isEmpty()) { //checks if password is empty  `           //check password
            lbl_password.setText("Password *required field *");
            lbl_password.setTextColor(Color.RED);
            return false;
        } else if (!isValidPassword(password)) { //checks if password is valid
            lbl_password.setText("Password * invalid password *");
            lbl_password.setTextColor(Color.RED);
            return false;
        } else {
            lbl_password.setText("Password");
            lbl_password.setTextColor(Color.BLACK);
            return true;
        }
    }

    public boolean FirstNameValidation(String firstName) {
        if (firstName.isEmpty()) {                                           //check first name
            lbl_firstName.setText("First Name *required field *");
            lbl_firstName.setTextColor(Color.RED);
            return false;
        } else {
            lbl_firstName.setText("First Name");
            lbl_firstName.setTextColor(Color.BLACK);
            return true;
        }
    }

    public boolean LastNameValidation(String LastName) {   //String LastName
        if (LastName.isEmpty()) {                                           //check first name
            lbl_lastName.setText("Last Name *required field *");
            lbl_lastName.setTextColor(Color.RED);
            return false;
        } else {
            lbl_lastName.setText("Last Name");
            lbl_lastName.setTextColor(Color.BLACK);
            return true;
        }
    }

    public boolean PhoneNumberValidation(String phone) {
        if (phone.isEmpty()) {                                           //check phone number
            lbl_phoneNumber.setText("Phone Number *required field *");
            lbl_phoneNumber.setTextColor(Color.RED);
            return false;
        } else {
            lbl_phoneNumber.setText("Phone Number");
            lbl_phoneNumber.setTextColor(Color.BLACK);
            return true;
        }
    }

    class PutCustomer implements Runnable {
        private Customer customer;

        public PutCustomer(Customer customer) {
            this.customer = customer;
        }

        @Override
        public void run() {
            //send JSON data to REST service
            String url = "http://192.168.133.1:8080/TravelExpertsOOSDJSP/rs/packagesalberta/putcustomer/";
            JSONObject obj = new JSONObject();
            try {
//                obj.put("custId", customer.getCustId() + "");
                obj.put("custFirstName", customer.getCustFirstName()+ "");
                obj.put("custLastName", customer.getCustLastName() + "");
                obj.put("custBusPhone", customer.getCustBusPhone() + "");
                obj.put("custEmail", customer.getCustEmail() + "");
                obj.put("Password", customer.getPassword() + ""); // name is case sensitive
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, obj,
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
        }
    }

}