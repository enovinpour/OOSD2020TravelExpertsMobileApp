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

    Package checkoutPackage;
    ImageView ivPackageBook, ivPackageMap, ivHotel, ivFood;
    ImageButton imgBtnBack;
    TextView tvPackageBookPgkName, tvPackageCostDesc, tvCost, tvFamilyFriendly, tvFoodIncluded, tvAbout;
    TextView tvLongDesc, tvLocation, tvCostFinal, tvHotelDetails, tvFoodDetails;
    RequestQueue requestQueue;
    Package listViewPackage;
    CardView cvHotel;
    CardView cvFood;
    Button btnBookNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_book2);
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
        //this is the package that was passed from the PackageSelect activity
        Package newPackage = (Package) intent.getSerializableExtra("packagePassed");

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


        //ivPackageBook.setImageResource(res_image);
        //tvPackageBookPgkName.setText(newPackage.getPkgName());
        //int res_image = newPackage.getPkgImageMain();
        //Bundle bundle = getIntent().getExtras();
        //if (bundle!=null) {
            //int res_image = bundle.getInt("imageNew");
            //ivPackageBook.setImageResource(res_image);
        //}

        ImageButton imgBtn = (ImageButton)findViewById(R.id.imgBtnBack);
        imgBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }
    class GetPackage implements Runnable {
        private int packageId;

        public GetPackage(int packageId) {
            this.packageId = packageId;
        }

        @Override
        public void run() {
            //retrieve JSON data from REST service into StringBuffer
            StringBuffer buffer = new StringBuffer();
            //String url = "http:/192.168.0.17:8081/JSPDay4RESTJPAExample/rs/agent/getagent/" + packageId;
           String url = "http://192.168.0.32:8080/TravelExpertsOOSDJSP2/rs/TEREST/getpackage/" + packageId;
           // String url = "http://192.168.0.12:8081/OOSDTravelExperts/rs/agent/getpackage/" + packageId; //For Ehsans Testing

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    VolleyLog.wtf(response, "utf-8");

                    //convert JSON data from response string into an Agent
                    JSONObject pkg = null;
                    try {
                        pkg = new JSONObject(response);
                        checkoutPackage = new Package(pkg.getInt("packageId"),pkg.getString("pkgName"),
                                pkg.getDouble("pkgBasePrice"), pkg.getString("pkgStartDate"), pkg.getString("pkgEndDate"),
                                pkg.getDouble("pkgAgencyCommission"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //update ListView with the adapter of Agents
                    final JSONObject finalPkg = pkg;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tvPackageBookPgkName.setText(finalPkg.getString("pkgName") + "");
                                String mainimageName = finalPkg.getString("pkgImageMain");
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
                                if (imageHotelId != 0) {
                                    cvHotel.setVisibility(View.VISIBLE);
                                    ivHotel.setImageResource(imageHotelId);
                                    tvHotelDetails.setText(finalPkg.getString("pkgHotelDesc"));
                                }

                                String imageFood = finalPkg.getString("pkgImageFood");
                                //Log.d("doug", "RESOURCE: " + imageHotel);
                                int imageFoodId = getResources().getIdentifier(imageFood, "drawable", getPackageName());
                                //Log.d("doug", "RESOURCE: " + imageHotelId);
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