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

    ListView lvPackageSelect;
    ArrayList<Package> imagesList;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_select);
        requestQueue = Volley.newRequestQueue(this);
        lvPackageSelect = findViewById(R.id.lvPackageSelect);
        /*String [] from = { "imgName" };
        int [] to = {R.id.ivPackagePicDisplay};
        SimpleAdapter adapter = new SimpleAdapter(this, loadData(),R.layout.package_pic_display, from, to);

        lvPackageSelect.setAdapter(adapter);*/

        Executors.newSingleThreadExecutor().execute(new GetPackages());
        lvPackageSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Package packageNew = (Package) lvPackageSelect.getAdapter().getItem(position);
                Intent intent = new Intent(getApplicationContext(), PackageBookActivity2.class);
                //Package packageNew = imagesList.get(position);
                intent.putExtra("packagePassed", packageNew);
                //int newResID = item.getPkgImageMain();
                //intent.putExtra("imageNew",newResID);
                startActivity(intent);
            }
        });
    }

/*    private ArrayList loadData() {

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
//the package tostring
    class GetPackages implements Runnable {
    @Override
    public void run() {
        //retrieve JSON data from REST service into StringBuffer
        StringBuffer buffer = new StringBuffer();
        //String url = "http://192.168.0.23:8082/JSPDay4RESTJPAExample/rs/agent/getagentlist";
//        String url = "http://192.168.0.17:8081/TravelExpertsOOSDJSP2/rs/packagesalberta/getpackagelist";
        String url = "http://192.168.0.12:8081/OOSDTravelExperts/rs/travel/getpackagelist"; //For Ehsans Testing

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VolleyLog.wtf(response, "utf-8");

                //convert JSON data from response string into an ArrayAdapter of Agents
                ArrayAdapter<Package> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
                //ArrayAdapter<Package> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject pkg = jsonArray.getJSONObject(i);

                        Package aPackage = new Package(pkg.getInt("PackageId"), pkg.getString("PkgName")                                  );
                        adapter.add(aPackage);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //update ListView with the adapter of Agents
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