package com.gomsang.lab.publicchain.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.ActivityMainBinding;
import com.gomsang.lab.publicchain.datas.AuthData;
import com.gomsang.lab.publicchain.datas.CampaignData;
import com.gomsang.lab.publicchain.libs.Constants;
import com.gomsang.lab.publicchain.libs.utils.VerifyUtil;
import com.gomsang.lab.publicchain.ui.dialogs.CampaignDialog;
import com.gomsang.lab.publicchain.ui.dialogs.CurrentCampaignsDialog;
import com.gomsang.lab.publicchain.ui.dialogs.OpenCampaignDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private ActivityMainBinding binding;
    private DatabaseReference database;
    private MenuItem signUpMenuItem;
    private MenuItem usersMenuItem;
    private AuthData currentAuthData;


    private HashMap<Marker, CampaignData> campaigns = new HashMap<>();
    private HashMap<Marker, String> nearby = new HashMap<>();
    private ArrayList<GeoQuery> geoQueries = new ArrayList<>();

    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        database = FirebaseDatabase.getInstance().getReference();

        setSupportActionBar(binding.parentPanel.toolbar);
        getSupportActionBar().setTitle("Public Chain");
/*
\
        Web3j web3 = Web3jFactory.build(new HttpService("http://nozzang.jubeat.ml:8545"));  // defaults to http://localhost:8545/
        web3.web3ClientVersion().observable().subscribe(x -> {
            String clientVersion = x.getWeb3ClientVersion();
            Log.d("blockchain", clientVersion);
        });
*/

       /* JSONArray ARR = LoadUtils.getToiletsJsonArray(this);
        DatabaseReference geoPark = database.child("opendatas").child("publics-geo");
        geoPark.removeValue();
        for (int a = 0; a < ARR.length(); a++) {
            try {
                JSONObject object = ARR.getJSONObject(a);
                GeoFire geoFire = new GeoFire(geoPark);

                try {
                    geoFire.setLocation(object.getString("name"), new GeoLocation(object.getDouble("latitude"),
                            object.getDouble("longitude")), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            count++;
                            Log.d("geoLogs", key + "|" + count);
                        }
                    });
                }catch (Exception e){
                    Log.d("geoLogs", e + "");

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("geoLogs", e + "");
            }
        }*/

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                initMap();
                // 기기 고유 토큰을 이용해 해당기기를 기반으로 등록된 계정이 있는지 찾습니다.
                database.child("users").child(VerifyUtil.generateDevicePrivateToken(MainActivity.this)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) return;
                        currentAuthData = dataSnapshot.getValue(AuthData.class);

                        // 네비게이션 메뉴단의 정보를 업데이트 합니다.
                        signUpMenuItem.setVisible(false);
                        usersMenuItem.setVisible(true);
                        View headerView = binding.navView.getHeaderView(0);
                        ((TextView) headerView.findViewById(R.id.usernameTextView)).setText(currentAuthData.getName());
                        ((TextView) headerView.findViewById(R.id.emailTextView)).setText(currentAuthData.getEmail());

                        Toast.makeText(MainActivity.this, "auth | Hello! " + currentAuthData.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        binding.parentPanel.fab.setOnClickListener((
                View view) ->
                Snackbar.make(view, "Long-click a place on the map where you want to start your campaign", Snackbar.LENGTH_LONG)
                        .

                                setAction("Action", null).

                        show()

        );

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.parentPanel.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);

        signUpMenuItem = binding.navView.getMenu().findItem(R.id.nav_signin);
        usersMenuItem = binding.navView.getMenu().findItem(R.id.nav_users);

        if (currentAuthData != null) {
            signUpMenuItem.setVisible(false);
            usersMenuItem.setVisible(true);
        } else {
            signUpMenuItem.setVisible(true);
            usersMenuItem.setVisible(false);
        }


        new TedPermission(this).setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    public void initMap() {
        MapFragment mapFragment = MapFragment.newInstance();
        mapFragment.getMapAsync(MainActivity.this);
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.contentFragment, mapFragment);
        fragmentTransaction.commit();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.SOUTHKOREA, 5));
        map.setMyLocationEnabled(true);
        map.setOnMapLongClickListener((LatLng latLng) -> {
            if (currentAuthData != null) {
                OpenCampaignDialog openCampaignDialog = new OpenCampaignDialog(MainActivity.this, currentAuthData.getPublicToken(), latLng);
                openCampaignDialog.show();
            } else {
                startActivity(new Intent(MainActivity.this, AuthActivity.class));
                Toast.makeText(MainActivity.this, "require registration for open campaign", Toast.LENGTH_SHORT).show();
            }
        });

        map.setOnMarkerClickListener(marker -> {

                    if (campaigns.containsKey(marker)) {
                        if (currentAuthData != null) {
                            final CampaignData campaignData = campaigns.get(marker);
                            CampaignDialog campaignDialog = new CampaignDialog(MainActivity.this, campaignData, currentAuthData);
                            campaignDialog.show();
                        } else {
                            startActivity(new Intent(MainActivity.this, AuthActivity.class));
                            Toast.makeText(MainActivity.this, "require registration for see campaign", Toast.LENGTH_SHORT).show();
                        }
                    }
                    return false;
                }
        );

        map.setOnMapClickListener((LatLng latLng) -> {
            for (Marker marker : nearby.keySet()) {
                marker.remove();
            }
            for (GeoQuery geoQuery : geoQueries){
                geoQuery.removeAllListeners();
            }
            geoQueries.clear();

            loadNearbyOpenData(map, latLng, "toilets");
            loadNearbyOpenData(map, latLng, "parks");
            loadNearbyOpenData(map, latLng, "publics");

            Toast.makeText(MainActivity.this, "search started", Toast.LENGTH_SHORT).show();
        });

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
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, getResources().getDisplayMetrics()), false);
        return resizedBitmap;
    }

    public void loadNearbyOpenData(GoogleMap map, LatLng latLng, String name) {
        DatabaseReference geoToilet = database.child("opendatas").child(name + "-geo");
        GeoFire geoFire = new GeoFire(geoToilet);
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
                System.out.println(String.format("Key %s is no longer in the search area", key));
            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                System.out.println(String.format("Key %s moved within the search area to [%f,%f]", key, location.latitude, location.longitude));
            }

            @Override
            public void onGeoQueryReady() {
                System.out.println("All initial data has been loaded and events have been fired!");
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                System.err.println("There was an error with this query: " + error);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {
            case R.id.nav_signin:
                startActivity(new Intent(MainActivity.this, AuthActivity.class));
                break;
            case R.id.nav_campaigns:
                CurrentCampaignsDialog currentCampaignsDialog = new CurrentCampaignsDialog(MainActivity.this, currentAuthData);
                currentCampaignsDialog.show();
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
