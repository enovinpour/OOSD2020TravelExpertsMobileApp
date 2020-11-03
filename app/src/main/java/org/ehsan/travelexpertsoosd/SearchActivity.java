package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    ListView lv;
    ArrayAdapter adapter;
    ArrayList<String>list1;
    SearchView sv;

    //Author: Sean Burman, we were not able to get it to function in time and we decided to keep this functionality out of the final demo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        lv=(ListView)findViewById(R.id.list);
        sv=(SearchView)findViewById(R.id.search);
        list1=new ArrayList<String>();
        list1.add("Polynesian Paradise");
        list1.add("Asian Expedition");
        list1.add("European Vacation");
        list1.add("Drumheller Fossil Excursion");
        list1.add("Waterton Weekend");

        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list1);
        lv.setAdapter(adapter);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}