package com.gomsang.lab.publicchain.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.FragmentRecoverBinding;

/**
 * Created by Gyeongrok Kim on 2017-08-08.
 */

public class RecoverFragment extends Fragment {

    FragmentRecoverBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recover, container, false);
        return binding.getRoot();
    }

}
