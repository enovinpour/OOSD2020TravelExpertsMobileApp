package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import Model.Package;


/*
Author: Ehsan Novin-Pour
 */
public class CheckoutActivity extends AppCompatActivity {

    //Global variables
    private String packageName;
    private String tripStart;
    private String tripEnd;
    private double price;
    private double GST;
    private double total;
    private int qty;
    private Package checkoutPackage;
    private Date startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Checkout");

        Intent intent = getIntent();
        //this is the package that was passed from the PackageBookActivity2 activity
        checkoutPackage = (Package) intent.getSerializableExtra("passedObject");

        packageName = checkoutPackage.getPkgName();
        tripStart = checkoutPackage.getPkgStartDate();
        tripEnd = checkoutPackage.getPkgEndDate();

        //Format the date data coming from the package object
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");

        try {
             startDate = format.parse(tripStart);
             endDate = format.parse(tripEnd);
             format.applyPattern("dd/MM/yyyy");
             tripStart = format.format(startDate);
             tripEnd = format.format(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }


//        java.text.DateFormat dateFormat = DateFormat.getMediumDateFormat(getApplicationContext());

        //Initiate the textview fields
        TextView tvCity = findViewById(R.id.tvCity);
        TextView tvTripDate = findViewById(R.id.tvTripDate);
        final NumberPicker npQty = findViewById(R.id.npQty);
        final TextView tvPrice = findViewById(R.id.tvPrice);
        final TextView tvGST = findViewById(R.id.tvGST);
        final TextView tvTotal = findViewById(R.id.tvTotal);
        final Button btnPay = findViewById(R.id.btnPay);

        //Initial Price Calculations
        price = checkoutPackage.getPkgBasePrice() + checkoutPackage.getPkgAgencyCommission();
        GST = price * 0.05;
        total = price + GST;
        qty = npQty.getValue();

        npQty.setMaxValue(4);
        npQty.setMinValue(1);

        //Number picker change view listener
        npQty.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                qty = npQty.getValue();
                price = (checkoutPackage.getPkgBasePrice() + checkoutPackage.getPkgAgencyCommission()) * qty;
                GST = price * 0.05;
                total = price + GST;

                tvPrice.setText("Price:   $" + String.valueOf(price));
                tvGST.setText("GST:   $" + String.valueOf(GST));
                tvTotal.setText("Total:   $" + String.valueOf(total));
            }
        });

        //Fill the textview fields
        tvCity.setText(packageName);
        tvTripDate.setText(tripStart);
        tvPrice.setText("Price:   $" + String.valueOf(price));
        tvGST.setText("GST:   $" + String.valueOf(GST));
        tvTotal.setText("Total:   $" + String.valueOf(total));

        //Start the Credit Card Activity
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreditCardActivity.class);
                intent.putExtra("Total", total);
                intent.putExtra("passedObject", checkoutPackage);
                intent.putExtra("qty",qty);
                startActivity(intent);
            }
        });
    }
}