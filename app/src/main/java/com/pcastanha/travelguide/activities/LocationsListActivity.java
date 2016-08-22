package com.pcastanha.travelguide.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.pcastanha.travelguide.fragments.LocationsListFragment;
import com.pcastanha.travelguide.R;

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
