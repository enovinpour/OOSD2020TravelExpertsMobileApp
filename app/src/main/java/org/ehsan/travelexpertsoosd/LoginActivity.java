package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.ehsan.travelexpertsoosd.Validator.isValidEmailNoAlert;
import static org.ehsan.travelexpertsoosd.Validator.isValidPassword;


public class LoginActivity extends AppCompatActivity {

    View view;
    EditText txt_email, txt_password;
    ImageView img_login;
    Button btn_submit;
    TextView lbl_email, lbl_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
}

}
