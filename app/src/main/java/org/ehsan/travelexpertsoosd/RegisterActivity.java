package org.ehsan.travelexpertsoosd;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static org.ehsan.travelexpertsoosd.Validator.isValidEmailNoAlert;
import static org.ehsan.travelexpertsoosd.Validator.isValidPassword;

public class RegisterActivity extends AppCompatActivity {

    View view;
    EditText txt_firstName, txt_lastName, txt_initial, txt_custPhone, txt_email, txt_password;
    Button btn_submit;
    TextView lbl_email, lbl_password, lbl_firstName, lbl_lastName, lbl_initial, lbl_phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                String initial = txt_initial.getText().toString();
                String phone = txt_custPhone.getText().toString();
                String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();

                if (email.isEmpty()) { //checks if email is empty
                    lbl_email.setText("Email *required field");
                    lbl_email.setTextColor(Color.RED);
                } else if (!isValidEmailNoAlert(email)) { //checks if email is valid
                    lbl_email.setText("Email * invalid email *");
                    lbl_email.setTextColor(Color.RED);
                } else {
                    String validEmail = email;  //else, email is valid - pass it to new validEmail variable
                }

                if (password.isEmpty()) { //checks if password is empty
                    lbl_password.setText("Password *required field *");
                    lbl_password.setTextColor(Color.RED);
                } else if (!isValidPassword(password)) { //checks if password is valid
                    lbl_password.setText("Password * invalid password *");
                    lbl_password.setTextColor(Color.RED);
                } else {  //this should only operate if the previous 2 if statements return true? (QUESTION THAT? )
                    String validPassword = password; // else, password is valid - pass it to validPassword variable
                }

                if (firstName.isEmpty()) {
                    lbl_firstName.setText("First Name *required field *");
                    lbl_firstName.setTextColor(Color.RED);
                } else {
                    String validFName = firstName;
                }
                if (lastName.isEmpty()) {
                    lbl_lastName.setText("Last Name *required field *");
                    lbl_lastName.setTextColor(Color.RED);
                } else {
                    String validLName = lastName;
                }
                if (initial.isEmpty()) {
                    lbl_initial.setText("Initial *required field *");
                    lbl_initial.setTextColor(Color.RED);
                } else {
                    String validInitial = initial;
                }
                if (phone.isEmpty()) {
                    lbl_phoneNumber.setText("Phone Number *required field *");
                    lbl_phoneNumber.setTextColor(Color.RED);
                }
            }
        });
    }
}