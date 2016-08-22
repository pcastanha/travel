package com.pcastanha.travelguide.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.pcastanha.travelguide.utils.ConstantsUtil;
import com.pcastanha.travelguide.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by pedro.matos.castanha on 6/24/2016.
 */
public class AddressLocationService extends IntentService {

    protected ResultReceiver mReceiver;
    private static final String TAG = AddressLocationService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public AddressLocationService() {
        super("TravelGuide");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String errorMessage = "";
        List<Address> addresses = null;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        // Initialize the ResultReceiver with the intent extra from the Activity.
        mReceiver = intent.getParcelableExtra(ConstantsUtil.RECEIVER);

        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra(ConstantsUtil.LOCATION_DATA_EXTRA);

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1); // In this sample, get just a single address.
        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            errorMessage = getString(R.string.service_not_available);
            Log.e(TAG, errorMessage, ioException);
        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            errorMessage = getString(R.string.invalid_lat_long_used);
            Log.e(TAG, errorMessage + ". " + "Latitude = " + location.getLatitude() + ", Longitude = " + location.getLongitude(), illegalArgumentException);
        }

        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0) {
            if (errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);
                Log.e(TAG, errorMessage);
            }
            deliverResultToReceiver(ConstantsUtil.FAILURE_CONST, errorMessage);
        } else {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }

            Log.i(TAG, getString(R.string.address_found));
            deliverResultToReceiver(ConstantsUtil.SUCCESS_CONST, TextUtils.join(System.getProperty("line.separator"), addressFragments));
        }
    }

    private void deliverResultToReceiver(int resultCode, String message) {
        Bundle bundle = new Bundle();
        bundle.putString(ConstantsUtil.RESULT_DATA_KEY, message);
        mReceiver.send(resultCode, bundle);
    }
}
