package com.example.asset_management.deviceCard.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.asset_management.R;
import com.example.asset_management.deviceCard.DeviceCardActivity;
import com.example.asset_management.recycleViewDeviceList.Device;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.sql.Timestamp;

/**
 * MapFragment
 * <p>
 *     Version 1.0
 * </p>
 * 30.08.2020
 * AUTHOR: Dominik Dziersan
 */
public class MapFragment extends Fragment {

    private MapViewModel mapViewModel;
    private MapView mapView;
    private GoogleMap googleMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mapViewModel =
                ViewModelProviders.of(this).get(MapViewModel.class);

        View root = inflater.inflate(R.layout.fragment_device_card_map, container,
                false);

        DeviceCardActivity activity = (DeviceCardActivity) getActivity();
        final Device device = activity.getDevice();

        mapView =root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        final TextView textMapDate = root.findViewById(R.id.textMapDate);

        if(device.getLongitude() == null
        || device.getLatitude() == null){
            textMapDate.setText(getString(R.string.noLocationText));
        } else {
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LatLng hs = new LatLng(device.getLongitude(),device.getLatitude());
                    googleMap.addMarker(new MarkerOptions().position(hs));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(hs));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(hs)
                            .zoom(15).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            });
        }

        Timestamp timestamp = device.getLastLocationUpdate();

        try {
            textMapDate.setText(getString(R.string.lastUpdateMessage) + timestamp.toString());
        } catch (Exception ignored){

        }
        return root;
    }
}