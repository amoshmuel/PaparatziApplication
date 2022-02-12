package com.main.paparatzi.View.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.main.paparatzi.Model.Photo;
import com.main.paparatzi.View.Activities.PhotoActivity;
import com.main.paparatzi.Control.FireBase;
import com.main.paparatzi.Control.GetListListener;
import com.main.paparatzi.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;

    public MapFragment() {
        Log.d("pttt", "MapFragment: Empty constructor");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d("pttt", "onCreateView: ");
        View view= inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.fragment_map);
        if(supportMapFragment!=null)
            supportMapFragment.getMapAsync(this);

        return view;
    }

    private void onDataChangeListener(){
        Log.d("pttt", "data: ");
        FireBase.getInstance().getPhotoCollection().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.d("pttt", "onEvent: ");
                populateMapMarkers();
            }
        });
    }



    private void populateMapMarkers() {
        googleMap.clear();
        FireBase.getInstance().getListItems(new GetListListener() {
            @Override
            public void getList(List<Photo> list) {
                for (Photo n : list)
                    addMarker(n);
            }
        });
    }

    private void addMarker(Photo n ){
        // Creating a marker
        MarkerOptions markerOptions = new MarkerOptions();
        // Setting the position for the marker
        markerOptions.position(new LatLng(n.getLat(),n.getLon()));
        markerOptions.title(n.getTitle());
        // Placing a marker on the touched position
        googleMap.addMarker(markerOptions).setTag(n);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("pttt", "onMapReady: ");
        this.googleMap =googleMap;
        this.googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(31.771959, 35.217018 ) , 8.0f) );
        populateMapMarkers();
        onDataChangeListener();
        addMarkerClickListener();

    }

    private void addMarkerClickListener() {
        this.googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                openPhotoActivity((Photo) marker.getTag());
            }
        });
    }

    private void openPhotoActivity(Photo tag) {
        Intent myIntent = new Intent(getContext(), PhotoActivity.class);
        myIntent.putExtra("photo",tag);
        ((Activity)getContext()).startActivityForResult(myIntent,RESULT_OK);
    }


}