package com.gomsang.lab.publicchain.ui.fragments.navigations;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
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
import com.gomsang.lab.publicchain.libs.PublicChainState;
import com.gomsang.lab.publicchain.libs.utils.LoadUtils;
import com.gomsang.lab.publicchain.ui.activities.Main1Activity;
import com.gomsang.lab.publicchain.ui.dialogs.CampaignDialog;
import com.gomsang.lab.publicchain.ui.dialogs.OpenCampaignDialog;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class LocateFragment extends Fragment implements OnMapReadyCallback {

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private DatabaseReference database;

    private PublicChainState publicChainState;

    private HashMap<Marker, CampaignData> campaigns = new HashMap<>();
    private HashMap<Marker, String> nearby = new HashMap<>();
    private ArrayList<GeoQuery> geoQueries = new ArrayList<>();

    private GoogleMap currentMap = null;
    private Marker searchedMarker = null;

    public LocateFragment() {
        // Required empty public constructor
        database = FirebaseDatabase.getInstance().getReference();
        publicChainState = PublicChainState.getInstance();
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
    public View onCreateView(@Nonnull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentLocateBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_locate, null, false);

        final SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        binding.searchButton.setOnClickListener((View view) -> {
            int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
            try {
                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                .build(getActivity());
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                // TODO: Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // TODO: Handle the error.
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && currentMap != null) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                if (searchedMarker != null) searchedMarker.remove();
                searchedMarker = currentMap.addMarker(new MarkerOptions()
                        .position(place.getLatLng())
                        .title(place.getName() + ""));
                currentMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                // TODO: Handle the error.
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled operation.
            }
        }

    }

    @Override
    public void onMapReady(GoogleMap map) {
        currentMap = map;
        // moving map to focus south korea. (on first)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.SOUTHKOREA, 5));
        if (!(ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            map.setMyLocationEnabled(true);
        }
        // when map click
        map.setOnMapClickListener((LatLng latLng) -> {
            for (Marker marker : nearby.keySet()) marker.remove();
            nearby.clear();
            for (GeoQuery geoQuery : geoQueries) geoQuery.removeAllListeners();
            geoQueries.clear();

            loadNearbyOpenData(map, latLng, new String[]{"toilets", "parks", "publics"});
        });
        // when map long-click
        map.setOnMapLongClickListener((LatLng latLng) -> {
            if (publicChainState.getCurrentUserData() != null) {
                OpenCampaignDialog openCampaignDialog = new OpenCampaignDialog(getActivity(),
                        publicChainState.getCurrentUserData().getUid(), latLng);
                openCampaignDialog.show();
            } else {
                Toast.makeText(getActivity(), "require registration for open campaign", Toast.LENGTH_SHORT).show();
            }
        });
        // on marker click -> show detail campaign information
        map.setOnMarkerClickListener(marker -> {
                    if (campaigns.containsKey(marker)) {
                        if (publicChainState != null) {
                            final CampaignData campaignData = campaigns.get(marker);
                            CampaignDialog campaignDialog = new CampaignDialog(getActivity(), campaignData, publicChainState.getCurrentUserData());
                            campaignDialog.show();
                        } else {
                            Toast.makeText(getActivity(), "require registration for see campaign", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return false;
                }
        );

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
                    markerOptions.icon(BitmapDescriptorFactory
                            .fromBitmap(LoadUtils.loadResizingSquareDrawable(getActivity(), "marker_for_donate", 28)));
                } else {
                    markerOptions.icon(BitmapDescriptorFactory
                            .fromBitmap(LoadUtils.loadResizingSquareDrawable(getActivity(), "marker_for_sign", 28)));
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
                            .icon(BitmapDescriptorFactory
                                    .fromBitmap(LoadUtils.loadResizingSquareDrawable(getActivity(), "marker_for_" + name, 28)));
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
