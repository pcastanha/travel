package com.pcastanha.travelguide;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InsertGuideActivity extends AppCompatActivity implements InsertGuideFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_guide);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
