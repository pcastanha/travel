package com.pcastanha.travelguide.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.pcastanha.travelguide.utils.ConstantsUtil;
import com.pcastanha.travelguide.R;
import com.pcastanha.travelguide.utils.TravelUtils;
import com.pcastanha.travelguide.activities.LocationsListActivity;
import com.pcastanha.travelguide.data.TravelGuideContract;
import com.pcastanha.travelguide.services.AddressLocationService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationsFragment extends Fragment implements LocationListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LoaderManager.LoaderCallbacks<Cursor> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private Location mLastLocation;
    private boolean hasIncomingTravels = false;
    private static final int URL_LOADER = 0;
    private static final int URL_LOADER_INSERT = 1;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private AddressResultReceiver mResultReceiver;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LOG_TAG = LocationsFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LocationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationsFragment newInstance(String param1, String param2) {
        LocationsFragment fragment = new LocationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private Bitmap mProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        Bitmap photo = BitmapFactory.decodeResource(getResources(), R.mipmap.profile_image);
        String tmp = TravelUtils.convertImageToByteArrayString(photo);
        mProfile = TravelUtils.convertByteArrayStringToBitmap(tmp);
        mResultReceiver = new AddressResultReceiver(new Handler());
        mGoogleApiClient.connect();
        createLocationRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (hasIncomingTravels) {
            // Inflate the layout for this fragment
            View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
            ListView locationList = (ListView) rootView.findViewById(R.id.main_activity_listView);

            String[] dummyList = {
                    "First Element",
                    "Second Element",
                    "Third Element",
                    "Fourth Element",
                    "Fifth Element"
            };

            List<String> dummyArray = new ArrayList<>(Arrays.asList(dummyList));

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                    R.layout.main_activity_single_item,
                    R.id.main_single_item,
                    dummyArray);

            locationList.setAdapter(adapter);

            locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent listIntent = new Intent(getActivity(), LocationsListActivity.class);
                    listIntent.putExtra("LOCATIONS_KEY", id);
                    startActivity(listIntent);
                    Toast newToast = Toast.makeText(getActivity(), "Item Clicked!",Toast.LENGTH_SHORT);
                    newToast.show();
                }
            });

            return rootView;
        } else {
            View rootView = inflater.inflate(R.layout.fragment_profile_empty, container, false);

            return rootView;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        /*
         * Initializes the CursorLoader. The URL_LOADER value is eventually passed
         * to onCreateLoader().
         */
        getLoaderManager().initLoader(URL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        if (mLastLocation != null) {
            System.out.println("Connected. Acquiring Location...");
            System.out.println("Lat: " + String.valueOf(mLastLocation.getLatitude()) + " Lon: " + String.valueOf(mLastLocation.getLongitude()));

            // Determine whether a Geocoder is available.
            if (!Geocoder.isPresent()) {
                Toast.makeText(getActivity(), R.string.no_geocoder_available,Toast.LENGTH_SHORT).show();
                return;
            }
            startIntentService();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Connection to Google Maps API failed.");
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("Initializing location change...");
        //String locationProvider = LocationManager.GPS_PROVIDER;

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Location lastKnownLocation = mLocationManager.getLastKnownLocation(locationProvider);
            if (null != location) {
                System.out.println("New location:");
                System.out.println("Lat: " + String.valueOf(location.getLatitude()) + " Lon: " + String.valueOf(location.getLongitude()));
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

     //
     // Takes action based on the ID of the Loader that's being created.
     //
        switch (id) {
            case URL_LOADER: {
                Uri mDataUrl = TravelGuideContract.ProfileEntry.buildProfileUri(1);

                return new CursorLoader(
                        getActivity(),   // Parent activity context
                        mDataUrl,        // Table to query
                        mProjection,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            }
            case URL_LOADER_INSERT: {
                ContentValues profileValues = new ContentValues();

                profileValues.put(TravelGuideContract.ProfileEntry.COLUMN_USERNAME, "Pedro Matos Castanha");
                profileValues.put(TravelGuideContract.ProfileEntry.COLUMN_UPCOMING, 2);
                profileValues.put(TravelGuideContract.ProfileEntry.COLUMN_CITIES, 30);
                profileValues.put(TravelGuideContract.ProfileEntry.COLUMN_LIKES, 22);
                profileValues.put(TravelGuideContract.ProfileEntry.COLUMN_COMPLETE, 4.3);
                //Uri mInsertProfileUrl = getContext().getContentResolver().insert(TravelGuideContract.ProfileEntry.CONTENT_URI, profileValues);

                //long tmpid = ContentUris.parseId(mInsertProfileUrl);

                //Log.d(LOG_TAG,"ID - " + tmpid);
            }

            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(LOG_TAG, "Loader execution finished. Processing result at onLoadFinished.");
        View profileRootView = getActivity().findViewById(R.id.main_activity_profile);
        Drawable d = new BitmapDrawable(getResources(), mProfile);

        if (data.moveToFirst()) {
            ((TextView) profileRootView.findViewById(R.id.profile_name_text)).setText(data.getString(COL_PROFILE_USERNAME));
            ((TextView) profileRootView.findViewById(R.id.profile_desc_text)).setText(data.getString(COL_PROFILE_COMPLETE));
            profileRootView.findViewById(R.id.profile_image_view).setBackground(d);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startIntentService() {
        Intent intentService = new Intent(getActivity(), AddressLocationService.class);
        intentService.putExtra(ConstantsUtil.RECEIVER, mResultReceiver);
        intentService.putExtra(ConstantsUtil.LOCATION_DATA_EXTRA, mLastLocation);
        getActivity().startService(intentService);
    }

    @SuppressLint("ParcelCreator")
    class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            Log.d("RESULT RECEIVER ", resultData.getString(ConstantsUtil.RESULT_DATA_KEY));
            Toast.makeText(getActivity(), resultData.getString(ConstantsUtil.RESULT_DATA_KEY),Toast.LENGTH_SHORT).show();
        }
    }

    // Projection of columns needed by the cursor loader.
    private static final String[] mProjection = {
            TravelGuideContract.ProfileEntry._ID,
            TravelGuideContract.ProfileEntry.COLUMN_CITIES,
            TravelGuideContract.ProfileEntry.COLUMN_COMPLETE,
            TravelGuideContract.ProfileEntry.COLUMN_PICTURE,
            TravelGuideContract.ProfileEntry.COLUMN_UPCOMING,
            TravelGuideContract.ProfileEntry.COLUMN_USERNAME,
            TravelGuideContract.ProfileEntry.COLUMN_LIKES
    };

    // These indices are tied to mProjection.  If it changes, these must change.
    static final int COL_PROFILE_ID = 0;
    static final int COL_PROFILE_CITIES = 1;
    static final int COL_PROFILE_COMPLETE = 2;
    static final int COL_PROFILE_PICTURE = 3;
    static final int COL_PROFILE_UPCOMING = 4;
    static final int COL_PROFILE_USERNAME = 5;
    static final int COL_PROFILE_LIKES = 6;
}