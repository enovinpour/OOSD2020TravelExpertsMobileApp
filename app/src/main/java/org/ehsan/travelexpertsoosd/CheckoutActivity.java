package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import Model.Package;

public class CheckoutActivity extends AppCompatActivity {

    private String packageName;
    private String tripStart;
    private String tripEnd;
    private double price;
    private double GST;
    private double total;
    private int qty;
    private Package checkoutPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Checkout");

        Intent intent = getIntent();
        //this is the package that was passed from the PackageSelect activity
        checkoutPackage = (Package) intent.getSerializableExtra("passedObject");

        packageName = checkoutPackage.getPkgName();
        tripStart = checkoutPackage.getPkgStartDate();
        tripEnd = checkoutPackage.getPkgEndDate();

//        java.text.DateFormat dateFormat = DateFormat.getMediumDateFormat(getApplicationContext());

        TextView tvCity = findViewById(R.id.tvCity);
        TextView tvTripDate = findViewById(R.id.tvTripDate);
        TextView tvPackage = findViewById(R.id.tvPackage);
        final NumberPicker npQty = findViewById(R.id.npQty);
        final TextView tvPrice = findViewById(R.id.tvPrice);
        final TextView tvGST = findViewById(R.id.tvGST);
        final TextView tvTotal = findViewById(R.id.tvTotal);
        final Button btnPay = findViewById(R.id.btnPay);

        String tripDate = tripStart + " - " + tripEnd;
        price = checkoutPackage.getPkgBasePrice() + checkoutPackage.getPkgAgencyCommission();
        GST = price * 0.05;
        total = price + GST;
        qty = npQty.getValue();

        npQty.setMaxValue(4);
        npQty.setMinValue(1);

        npQty.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                qty = npQty.getValue();
                price = (checkoutPackage.getPkgBasePrice() + checkoutPackage.getPkgAgencyCommission()) * qty;
                GST = price * 0.05;
                total = price + GST;

                tvPrice.setText("$" + String.valueOf(price));
                tvGST.setText("$" + String.valueOf(GST));
                tvTotal.setText("$" + String.valueOf(total));
            }
        });

        tvCity.setText(packageName);
        tvTripDate.setText(tripDate);
        tvPackage.setText("pckge");
        tvPrice.setText("$" + String.valueOf(price));
        tvGST.setText("$" + String.valueOf(GST));
        tvTotal.setText("$" + String.valueOf(total));

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreditCardActivity.class);
                intent.putExtra("Total", total);
                intent.putExtra("passedObject", checkoutPackage);
                startActivity(intent);
            }
        });
    }
}