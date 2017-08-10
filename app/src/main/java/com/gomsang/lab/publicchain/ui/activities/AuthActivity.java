package com.gomsang.lab.publicchain.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.ui.fragments.AuthHubFragment;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);

        replaceCover(new AuthHubFragment());
    }

    // 액티비티에 위치한 프래그먼트 (회원가입, 로그인) 전환
    public void replaceCover(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out,R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.contentLayout, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.contentLayout) instanceof AuthHubFragment) {
            finish();
        } else {
            replaceCover(new AuthHubFragment());
        }
    }

}
