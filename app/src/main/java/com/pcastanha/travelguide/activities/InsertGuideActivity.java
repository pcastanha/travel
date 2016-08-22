package com.pcastanha.travelguide.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.pcastanha.travelguide.fragments.InsertGuideFragment;
import com.pcastanha.travelguide.R;

public class InsertGuideActivity extends AppCompatActivity implements InsertGuideFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_guide);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_insertion, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                /*Uri mInsertGuideUrl = getContentResolver().insert(TravelGuideContract.GuideEntry.CONTENT_URI, null);*/
                Toast newToast = Toast.makeText(this, "Item Clicked! " /* + ContentUris.parseId(mInsertGuideUrl)*/,Toast.LENGTH_SHORT);
                newToast.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
