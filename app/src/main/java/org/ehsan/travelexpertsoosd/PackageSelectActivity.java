/*
 * Threaded Project Term 3 Workspace Workshop 8
 * Purpose: This is the code behind for the activity that displays
 * a list of travel packages in a listview
 * Author: Group 2 - Doug Cameron primary developer of this page
 * Date: Oct, 2020
 */

package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;

import Model.Package;



public class PackageSelectActivity extends AppCompatActivity {

    //define the controls for the page
    ListView lvPackageSelect;
    ArrayList<Package> imagesList;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_select);


        requestQueue = Volley.newRequestQueue(this);
        lvPackageSelect = findViewById(R.id.lvPackageSelect);

        //execute the GetPackages class
        Executors.newSingleThreadExecutor().execute(new GetPackages());

        //define a click event on the listview of packages
        lvPackageSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Package packageNew = (Package) lvPackageSelect.getAdapter().getItem(position);
                //define an intent to start the next tab which will display the package information
                Intent intent = new Intent(getApplicationContext(), PackageBookActivity2.class);

                intent.putExtra("packagePassed", packageNew);//pass a package object to the intent
                startActivity(intent);
            }
        });
    }

/* This was test code where I was trying to display the images on the listview instead of the toString text
private ArrayList loadData() {

        //create some courses to display in the listview
        String pictureName1 = "drumheller";
        String pictureName2 = "waterton";
        String pictureName3 = "package3";
        int resID1 = getResources().getIdentifier(pictureName1, "drawable", getPackageName());
        int resID2 = getResources().getIdentifier(pictureName2, "drawable", getPackageName());
        int resID3 = getResources().getIdentifier(pictureName3, "drawable", getPackageName());
        Log.d("harv", "RESOURCE: " + resID1);

        //read the packages
        imagesList = new ArrayList<>();
        imagesList.add( new Package(resID1,"Drumheller"));
        imagesList.add( new Package(resID2, "Weekend in Waterton"));
        imagesList.add( new Package(resID3, "Banff"));
        //read with a service call

        //Set up arraylist to map each course to the listview items
        ArrayList<HashMap<String, Integer>> data = new ArrayList<>();
        for (Package p : imagesList)
        {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("imgName", p.getPkgImageMain());
            data.add(map);

        }
        return  data;
    }*/

//this class reads the data from database and displays
//the package tostring in a list view
    class GetPackages implements Runnable {
    @Override
    public void run() {
        //retrieve JSON data from REST service into StringBuffer
        StringBuffer buffer = new StringBuffer();

        //preform the service calls to the webservice
        //String url = "http://192.168.0.23:8082/JSPDay4RESTJPAExample/rs/agent/getagentlist";
       String url = "http://192.168.0.32:8080/TravelExpertsOOSDJSP2/rs/TEREST/getpackagelist";
        //String url = "http://192.168.0.12:8081/OOSDTravelExperts/rs/agent/getpackagelist"; //For Ehsans Testing

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VolleyLog.wtf(response, "utf-8");

                //convert JSON data from response string into an ArrayAdapter of Agents
                ArrayAdapter<Package> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);

                //loop through JSON array
                try {
                    JSONArray jsonArray = new JSONArray(response);//create a new json array
                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject pkg = jsonArray.getJSONObject(i);//set a new json array called pkg

                        //create a new package to pass to the next tab PackageBookActivity
                        Package aPackage = new Package(pkg.getInt("PackageId"), pkg.getString("PkgName")                                  );
                        adapter.add(aPackage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //update ListView with the adapter of packages
                final ArrayAdapter<Package> finalAdapter = adapter;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lvPackageSelect.setAdapter(finalAdapter);
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