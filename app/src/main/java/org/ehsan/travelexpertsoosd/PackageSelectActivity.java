package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import Model.Package;

public class PackageSelectActivity extends AppCompatActivity {

    ListView lvPackageSelect;
    ArrayList<Package> imagesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_select);

        lvPackageSelect = findViewById(R.id.lvPackageSelect);
        String [] from = { "imgName" };
        int [] to = {R.id.ivPackagePicDisplay};
        SimpleAdapter adapter = new SimpleAdapter(this, loadData(),R.layout.package_pic_display, from, to);

        lvPackageSelect.setAdapter(adapter);

        lvPackageSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), PackageBookActivity2.class);
                Package packageNew = imagesList.get(position);
                intent.putExtra("packagePassed", packageNew);
                //int newResID = item.getPkgImageMain();
                //intent.putExtra("imageNew",newResID);



                startActivity(intent);
            }
        });
    }

    private ArrayList loadData() {

        //create some courses to display in the listview
        String pictureName1 = "drumheller";
        String pictureName2 = "waterton";
        String pictureName3 = "package3";
        int resID1 = getResources().getIdentifier(pictureName1, "drawable", getPackageName());
        int resID2 = getResources().getIdentifier(pictureName2, "drawable", getPackageName());
        int resID3 = getResources().getIdentifier(pictureName3, "drawable", getPackageName());
        Log.d("harv", "RESOURCE: " + resID1);

        imagesList = new ArrayList<>();
        imagesList.add( new Package(resID1,"Drumheller"));
        imagesList.add( new Package(resID2, "Weekend in Waterton"));
        imagesList.add( new Package(resID3, "Banff"));


        //Set up arraylist to map each course to the listview items
        ArrayList<HashMap<String, Integer>> data = new ArrayList<>();
        for (Package p : imagesList)
        {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("imgName", p.getPkgImageMain());
            data.add(map);

        }
        return  data;
    }
}