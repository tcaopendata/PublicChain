package com.gomsang.lab.publicchain.ui.fragments.navigations;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.FragmentLocateBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class LocateFragment extends Fragment implements OnMapReadyCallback {

    public LocateFragment() {
        // Required empty public constructor
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
    public void onMapReady(GoogleMap googleMap) {

    }
}
