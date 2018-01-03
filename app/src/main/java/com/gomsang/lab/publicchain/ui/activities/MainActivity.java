package com.gomsang.lab.publicchain.ui.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.ActivityMainBinding;
import com.gomsang.lab.publicchain.datas.UserData;
import com.gomsang.lab.publicchain.libs.PublicChainState;
import com.gomsang.lab.publicchain.ui.dialogs.InputDetailDialog;
import com.gomsang.lab.publicchain.ui.fragments.navigations.CommunityFragment;
import com.gomsang.lab.publicchain.ui.fragments.navigations.DashboardFragment;
import com.gomsang.lab.publicchain.ui.fragments.navigations.LocateFragment;
import com.gomsang.lab.publicchain.ui.fragments.navigations.MoreFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ActivityMainBinding binding;

    private FirebaseDatabase database;

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private UserData currentUserData;

    private static final int RC_SIGN_IN = 123;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    replaceNav(DashboardFragment.newInstance());
                    return true;
                case R.id.navigation_map:
                    replaceNav(LocateFragment.newInstance());
                    return true;
                case R.id.navigation_community:
                    replaceNav(CommunityFragment.newInstance());
                    return true;
                case R.id.navigation_more:
                    replaceNav(MoreFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_map);

        database = FirebaseDatabase.getInstance();

        auth = FirebaseAuth.getInstance();
        auth.useAppLanguage();

        mAuthListener = (@NonNull FirebaseAuth firebaseAuth) -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                database.getReference().child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            new InputDetailDialog(MainActivity.this).show();
                        } else {
                            currentUserData = dataSnapshot.getValue(UserData.class);
                            PublicChainState.getInstance().updateUserData(currentUserData);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            } else {
                // Choose authentication providers
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build());

                // Create and launch sign-in intent
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            auth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
    }


    private void replaceNav(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.contentLayout, fragment)
                .commit();
    }


}
