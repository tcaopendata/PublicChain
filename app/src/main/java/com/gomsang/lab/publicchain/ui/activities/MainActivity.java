package com.gomsang.lab.publicchain.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.ActivityMainBinding;
import com.gomsang.lab.publicchain.ui.fragments.navigations.CommunityFragment;
import com.gomsang.lab.publicchain.ui.fragments.navigations.DashboardFragment;
import com.gomsang.lab.publicchain.ui.fragments.navigations.LocateFragment;
import com.gomsang.lab.publicchain.ui.fragments.navigations.MoreFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ActivityMainBinding binding;

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

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void replaceNav(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.contentLayout, fragment)
                .commit();
    }


}
