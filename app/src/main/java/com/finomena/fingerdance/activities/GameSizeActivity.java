package com.finomena.fingerdance.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.finomena.fingerdance.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adarsh on 20/02/17.
 */

public class GameSizeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    Spinner spinner;
    Button button;
    int selection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        InitialiseView();
    }

    private void InitialiseView(){
        // Spinner element
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        categories.add("6");
        categories.add("7");
        categories.add("8");
        categories.add("9");
        categories.add("10");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        PackageManager pm = getPackageManager();
        boolean deviceSupportsMultiTouchFivePlus = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND);
        boolean deviceSupportsMultiTouchDistint = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT);
        boolean deviceSupportsMultiTouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);

        if(deviceSupportsMultiTouchFivePlus){
            //Device support more than 5 touch points
        }else if(deviceSupportsMultiTouchDistint){
            //Device support more than 2 touch points
        }else if(deviceSupportsMultiTouch){
            //Device support more than 2 touch points
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // On selecting a spinner item
        String item = adapterView.getItemAtPosition(i).toString();
        selection = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(GameSizeActivity.this, GameActivity.class);
        intent.putExtra("SELECTION", selection);
        startActivity(intent);
    }
}
