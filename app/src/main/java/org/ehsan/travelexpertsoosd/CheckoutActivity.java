package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

public class CheckoutActivity extends AppCompatActivity {

    String city = "Banff";
    Date tripStart = Calendar.getInstance().getTime();
    Date tripEnd = Calendar.getInstance().getTime();
    String pckge = "One of the packages";
    int qty = 1;
    double price = 230.00;
    double GST;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Checkout");

        java.text.DateFormat dateFormat = DateFormat.getMediumDateFormat(getApplicationContext());

        TextView tvCity = findViewById(R.id.tvCity);
        TextView tvTripDate = findViewById(R.id.tvTripDate);
        TextView tvPackage = findViewById(R.id.tvPackage);
        TextView tvQuantity = findViewById(R.id.tvQuantity);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvGST = findViewById(R.id.tvGST);
        TextView tvTotal = findViewById(R.id.tvTotal);
        Button btnPay = findViewById(R.id.btnPay);

        String tripDate = dateFormat.format(tripStart) + " - " + dateFormat.format(tripEnd);
        GST = price * 0.05;
        total = price + GST;

        tvCity.setText(city);
        tvTripDate.setText(tripDate);
        tvPackage.setText(pckge);
        tvQuantity.setText(String.valueOf(qty));
        tvPrice.setText("$" + String.valueOf(price));
        tvGST.setText("$" + String.valueOf(GST));
        tvTotal.setText("$" + String.valueOf(total));

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreditCardActivity.class);
                //lvPictures.setAdapter(adapter);
                startActivity(intent);
            }
        });
    }
}