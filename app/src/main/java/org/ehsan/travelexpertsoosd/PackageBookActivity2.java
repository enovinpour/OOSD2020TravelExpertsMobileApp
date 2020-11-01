/*
 * Threaded Project Term 3 Workspace Workshop 8
 * Purpose: This is the code behind for the activity that displays all the information about
 * a travel package that was selected in the PackageSelectActivity
 * Author: Group 2 - Doug Cameron and Eshan Novin-Pour were primary developers of this page
 * Date: Oct, 2020
 */

package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.Executors;

import Model.Package;

public class PackageBookActivity2 extends AppCompatActivity {
    //define the controls used in the page
    Package checkoutPackage;
    ImageView ivPackageBook, ivPackageMap, ivHotel, ivFood;
    TextView tvPackageBookPgkName, tvPackageCostDesc, tvCost, tvFamilyFriendly, tvFoodIncluded, tvAbout;
    TextView tvLongDesc, tvLocation, tvCostFinal, tvHotelDetails, tvFoodDetails;
    RequestQueue requestQueue;
    CardView cvHotel;
    CardView cvFood;
    Button btnBookNow;

    //the on create method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_book2);

        //bind the control variables to the XML activity controls
        requestQueue = Volley.newRequestQueue(this);
        ivPackageBook = findViewById(R.id.ivPackageBook);
        tvPackageBookPgkName = findViewById(R.id.tvPackageBookPgkName);
        tvPackageCostDesc = findViewById(R.id.tvCostDescription);
        tvCost = findViewById(R.id.tvCost);
        tvFamilyFriendly = findViewById(R.id.tvFamilyFriendly);
        tvFoodIncluded = findViewById(R.id.tvFoodIncluded);
        tvAbout = findViewById(R.id.tvAbout);
        tvLongDesc = findViewById(R.id.tvDescription);
        ivPackageMap = findViewById(R.id.ivPackageMap);
        tvLocation = findViewById(R.id.tvLocation);
        tvCostFinal = findViewById(R.id.tvCostFinal);
        ivHotel = findViewById(R.id.ivHotel);
        ivFood = findViewById(R.id.ivFood);
        cvHotel = findViewById(R.id.cvHotel);
        cvHotel.setVisibility(View.GONE);
        cvFood = findViewById(R.id.cvFood);
        cvFood.setVisibility(View.GONE);
        btnBookNow = findViewById(R.id.btnBookNow);
        tvHotelDetails = findViewById(R.id.tvHotelDetails);
        tvFoodDetails = findViewById(R.id.tvFoodDetails);

        Intent intent = getIntent();
        //retrieve the package object that was passed from the PackageSelect activity and
        //create a new package
        Package newPackage = (Package) intent.getSerializableExtra("packagePassed");

        //execute the thread and pass the retrieved package id to the getPackage class
        Executors.newSingleThreadExecutor().execute(new GetPackage(newPackage.getPackageId()));

        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                //pass the select package object to the checkout page
                intent.putExtra("passedObject", checkoutPackage );
                startActivity(intent);
            }
        });

        ImageButton imgBtn = (ImageButton)findViewById(R.id.imgBtnBack);
        imgBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    //this class retrieves data from the database for the packages
    //and displays the information in the page text view etc.
    class GetPackage implements Runnable {
        private int packageId;

        public GetPackage(int packageId) {
            this.packageId = packageId;
        }

        @Override
        public void run() {
            //retrieve JSON data from REST service into StringBuffer
            StringBuffer buffer = new StringBuffer();
            //make the service call to the webservice
            //String url = "http:/192.168.0.17:8081/JSPDay4RESTJPAExample/rs/agent/getagent/" + packageId;
           String url = "http://192.168.0.32:8080/TravelExpertsOOSDJSP2/rs/TEREST/getpackage/" + packageId;
           // String url = "http://192.168.0.12:8081/OOSDTravelExperts/rs/agent/getpackage/" + packageId; //For Ehsans Testing

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    VolleyLog.wtf(response, "utf-8");

                    //convert JSON data from response string into a package object
                    JSONObject pkg = null;
                    try {
                        pkg = new JSONObject(response); //set the package object to pkg
                        //create a object to pass to the checkout page
                        checkoutPackage = new Package(pkg.getInt("packageId"),pkg.getString("pkgName"),
                                pkg.getDouble("pkgBasePrice"), pkg.getString("pkgStartDate"), pkg.getString("pkgEndDate"),
                                pkg.getDouble("pkgAgencyCommission"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //update ListView with the adapter of Packages
                    final JSONObject finalPkg = pkg;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                //set all the controls to display the retrieved data
                                tvPackageBookPgkName.setText(finalPkg.getString("pkgName") + "");
                                String mainimageName = finalPkg.getString("pkgImageMain");
                                //take the image name retrieved from the database and finds it's resource id
                                int imageMainId = getResources().getIdentifier(mainimageName, "drawable", getPackageName());
                                ivPackageBook.setImageResource(imageMainId);
                                tvPackageCostDesc.setText(finalPkg.getString("pkgCostDesc") + "");

                                //format the double number from database into currency format
                                NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CANADA);
                                String currencyNumber = format.format(finalPkg.getDouble("pkgBasePrice"));
                                tvCost.setText(finalPkg.getString("pkgCurrencyType") + " " +  currencyNumber  );

                                tvFamilyFriendly.setText(finalPkg.getString("pkgFamilyFriendly"));
                                tvFoodIncluded.setText(finalPkg.getString("pkgFoodIncluded"));
                                tvAbout.setText((finalPkg.getString("pkgDesc")));
                                tvLongDesc.setText(finalPkg.getString("pkgLongDesc"));

                                String mainimageMap = finalPkg.getString("pkgImageMap");
                                int imageMapId = getResources().getIdentifier(mainimageMap, "drawable", getPackageName());
                                ivPackageMap.setImageResource(imageMapId);

                                tvLocation.setText(finalPkg.getString("pkgLocation"));
                                tvCostFinal.setText(finalPkg.getString("pkgCurrencyType") + " " +  currencyNumber  );

                                String imageHotel = finalPkg.getString("pkgImageHotel");
                                //Log.d("doug", "RESOURCE: " + imageHotel);
                                int imageHotelId = getResources().getIdentifier(imageHotel, "drawable", getPackageName());
                                //Log.d("doug", "RESOURCE: " + imageHotelId);

                                //if a hotel image exists in database, then display the hotel cardview
                                if (imageHotelId != 0) {
                                    cvHotel.setVisibility(View.VISIBLE);
                                    ivHotel.setImageResource(imageHotelId);
                                    tvHotelDetails.setText(finalPkg.getString("pkgHotelDesc"));
                                }

                                String imageFood = finalPkg.getString("pkgImageFood");
                                int imageFoodId = getResources().getIdentifier(imageFood, "drawable", getPackageName());
                                //if the image exist in the database for the food, then display the food cardview
                                if (imageFoodId != 0) {
                                    cvFood.setVisibility(View.VISIBLE);
                                    ivFood.setImageResource(imageFoodId);
                                    tvFoodDetails.setText(finalPkg.getString("pkgFoodDesc"));
                                }

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


}