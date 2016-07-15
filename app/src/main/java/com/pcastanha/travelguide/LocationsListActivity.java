package com.pcastanha.travelguide;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class LocationsListActivity extends AppCompatActivity implements LocationsListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LocationsListActivity.class.getSimpleName(), Long.toString(getIntent().getLongExtra("LOCATIONS_KEY", 123)));
        setContentView(R.layout.activity_locations_list);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
