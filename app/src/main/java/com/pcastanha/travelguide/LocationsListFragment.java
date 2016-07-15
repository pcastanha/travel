package com.pcastanha.travelguide;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LocationsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LocationsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LocationsListFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LocationsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LocationsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LocationsListFragment newInstance(String param1, String param2) {
        LocationsListFragment fragment = new LocationsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_locations_list, container, false);
        ListView locationList = (ListView) rootView.findViewById(R.id.listView);
        //MapFragment mapFragment = MapFragment.newInstance(initMapOptions());

        String[] dummyList = {
                "First Element - Location Name - Other stuff",
                "Second Element - Location Name - Other stuff",
                "Third Element - Location Name - Other stuff",
                "Fourth Element - Location Name - Other stuff",
                "Fifth Element - Location Name - Other stuff"
        };

        List<String> dummyArray = new ArrayList<>(Arrays.asList(dummyList));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                R.layout.main_activity_single_item,
                R.id.main_single_item,
                dummyArray);

        locationList.setAdapter(adapter);

        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(LocationsListFragment.class.getSimpleName(), "MapReady");
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-22.7146,-47.656),13));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private GoogleMapOptions initMapOptions(){
        GoogleMapOptions options = new GoogleMapOptions();

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(-22.7146,-47.656))       // Sets the center of the map to Mountain View
                .zoom(13)                                   // Sets the zoom
                .bearing(0)                                 // Sets the orientation of the camera to east
                .tilt(90)                                   // Sets the tilt of the camera to 30 degrees
                .build();                                   // Creates a CameraPosition from the builder

        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
                .compassEnabled(false)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false)
                .camera(cameraPosition);

        return options;
    }
}
