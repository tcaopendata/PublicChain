package com.gomsang.lab.publicchain.ui.fragments.navigations;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.FragmentLocateBinding;
import com.gomsang.lab.publicchain.datas.CampaignData;
import com.gomsang.lab.publicchain.libs.Constants;
import com.gomsang.lab.publicchain.ui.activities.AuthActivity;
import com.gomsang.lab.publicchain.ui.activities.Main1Activity;
import com.gomsang.lab.publicchain.ui.dialogs.CampaignDialog;
import com.gomsang.lab.publicchain.ui.dialogs.OpenCampaignDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class LocateFragment extends Fragment implements OnMapReadyCallback {

    private DatabaseReference database;

    private HashMap<Marker, CampaignData> campaigns = new HashMap<>();
    private HashMap<Marker, String> nearby = new HashMap<>();
    private ArrayList<GeoQuery> geoQueries = new ArrayList<>();

    public LocateFragment() {
        // Required empty public constructor
        database = FirebaseDatabase.getInstance().getReference();
    }

    public static LocateFragment newInstance() {
        LocateFragment fragment = new LocateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentLocateBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_locate, null, false);
        final SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.SOUTHKOREA, 5));
        map.setMyLocationEnabled(true);
        map.setOnMapClickListener((LatLng latLng) -> {
            for (Marker marker : nearby.keySet()) marker.remove();
            nearby.clear();
            for (GeoQuery geoQuery : geoQueries) geoQuery.removeAllListeners();
            geoQueries.clear();

            loadNearbyOpenData(map, latLng, new String[]{"toilets", "parks", "publics"});
        });

        /*map.setOnMapLongClickListener((LatLng latLng) -> {
            if (currentAuthData != null) {
                OpenCampaignDialog openCampaignDialog = new OpenCampaignDialog(Main1Activity.this, currentAuthData.getPublicToken(), latLng);
                openCampaignDialog.show();
            } else {
                startActivity(new Intent(Main1Activity.this, AuthActivity.class));
                Toast.makeText(Main1Activity.this, "require registration for open campaign", Toast.LENGTH_SHORT).show();
            }
        });

        map.setOnMarkerClickListener(marker -> {
                    if (campaigns.containsKey(marker)) {
                        if (currentAuthData != null) {
                            final CampaignData campaignData = campaigns.get(marker);
                            CampaignDialog campaignDialog = new CampaignDialog(Main1Activity.this, campaignData, currentAuthData);
                            campaignDialog.show();
                        } else {
                            startActivity(new Intent(Main1Activity.this, AuthActivity.class));
                            Toast.makeText(Main1Activity.this, "require registration for see campaign", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return false;
                }
        );*/

        // 모든 서명 데이터를 지도에 표시
        database.child("campaigns").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                CampaignData campaignData = dataSnapshot.getValue(CampaignData.class);
                if (campaignData == null) return;

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(campaignData.getLatitude(), campaignData.getLongitude()))
                        .title(campaignData.getName());

                if (campaignData.isFunding()) {
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker_for_donate")));
                } else {
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker_for_sign")));
                }

                Marker newMarker = map.addMarker(markerOptions);
                campaigns.put(newMarker, campaignData);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public Bitmap resizeMapIcons(String iconName) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, getResources().getDisplayMetrics()), false);
        return resizedBitmap;
    }

    public void loadNearbyOpenData(GoogleMap map, LatLng latLng, String[] targetNames) {
        for (String name : targetNames) {
            DatabaseReference geoRef = database.child("opendatas").child(name + "-geo");
            GeoFire geoFire = new GeoFire(geoRef);
            GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(latLng.latitude, latLng.longitude), 1);
            geoQueries.add(geoQuery);
            geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                @Override
                public void onKeyEntered(String key, GeoLocation location) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(location.latitude, location.longitude))
                            .title(key)
                            .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker_for_" + name)));
                    nearby.put(map.addMarker(markerOptions), key);
                }

                @Override
                public void onKeyExited(String key) {
                }

                @Override
                public void onKeyMoved(String key, GeoLocation location) {
                }

                @Override
                public void onGeoQueryReady() {
                }

                @Override
                public void onGeoQueryError(DatabaseError error) {
                }
            });
        }
    }
}
