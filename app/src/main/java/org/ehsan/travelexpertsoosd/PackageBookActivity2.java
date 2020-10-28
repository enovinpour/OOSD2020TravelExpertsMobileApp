package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.concurrent.Executors;

import Model.Package;

public class PackageBookActivity2 extends AppCompatActivity {

    ImageView ivPackageBook;
    ImageButton imgBtnBack;
    TextView tvPackageBookPgkName;
    TextView tvPackageCostDesc;
    RequestQueue requestQueue;
    Package listViewPackage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_book2);
        requestQueue = Volley.newRequestQueue(this);
        ivPackageBook = findViewById(R.id.ivPackageBook);
        tvPackageBookPgkName = findViewById(R.id.tvPackageBookPgkName);
        tvPackageCostDesc = findViewById(R.id.tvCostDescription);

        Intent intent = getIntent();
        Package newPackage = (Package) intent.getSerializableExtra("packagePassed");




        Executors.newSingleThreadExecutor().execute(new GetPackage(newPackage.getPackageId()));
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
            String url = "http://192.168.0.17:8081/TravelExpertsOOSDJSP2/rs/packagesalberta/getpackage/" + packageId;
            Log.d("doug", "RESOURCE: " + url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    VolleyLog.wtf(response, "utf-8");

                    //convert JSON data from response string into an Agent
                    JSONObject pkg = null;
                    try {
                        pkg = new JSONObject(response);
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
                                int resID1 = getResources().getIdentifier(mainimageName, "drawable", getPackageName());
                                ivPackageBook.setImageResource(resID1);
                                tvPackageCostDesc.setText(finalPkg.getString("pkgCostDesc") + "");

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