package com.gomsang.lab.publicchain.ui.fragments;

import android.support.v4.app.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.FragmentAuthBinding;
import com.gomsang.lab.publicchain.ui.activities.AuthActivity;

/**
 * Created by Gyeongrok Kim on 2017-08-08.
 */

public class AuthHubFragment extends Fragment {

    FragmentAuthBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false);
        binding.signUpButton.setOnClickListener(view -> ((AuthActivity) getActivity()).replaceCover(new SignUpFragment()));
        binding.recoverButton.setOnClickListener(view -> ((AuthActivity) getActivity()).replaceCover(new RecoverFragment()));
        return binding.getRoot();
    }
}
