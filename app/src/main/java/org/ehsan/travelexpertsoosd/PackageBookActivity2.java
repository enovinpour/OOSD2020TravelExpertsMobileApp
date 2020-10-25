package org.ehsan.travelexpertsoosd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import Model.Package;

public class PackageBookActivity2 extends AppCompatActivity {

    ImageView ivPackageBook;
    ImageButton imgBtnBack;
    TextView tvPackageBookPgkName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_book2);
        ivPackageBook = findViewById(R.id.ivPackageBook);
        tvPackageBookPgkName = findViewById(R.id.tvPackageBookPgkName);

        Intent intent = getIntent();
        Package newPackage = (Package) intent.getSerializableExtra("packagePassed");

        int res_image = newPackage.getPkgImageMain();
        ivPackageBook.setImageResource(res_image);
        tvPackageBookPgkName.setText(newPackage.getPkgName());

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


}