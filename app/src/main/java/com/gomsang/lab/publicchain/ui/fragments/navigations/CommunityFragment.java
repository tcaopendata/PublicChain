package com.gomsang.lab.publicchain.ui.fragments.navigations;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.FragmentCommunityBinding;

import javax.annotation.Nonnull;

public class CommunityFragment extends Fragment {
    public String parameter;

    public CommunityFragment() {
    }

    public static DashboardFragment newInstance(String parameter) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString("parameter", parameter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nonnull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            parameter = getArguments().getString("parameter");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FragmentCommunityBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_community, null, false);
        final SortPagerAdapter sortPagerAdapter = new SortPagerAdapter(getFragmentManager());
        binding.sortPager.setAdapter(sortPagerAdapter);
        binding.sortPager.setOffscreenPageLimit(3);
        binding.sortTabs.setupWithViewPager(binding.sortPager);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public class SortPagerAdapter extends FragmentStatePagerAdapter {

        private String[] pageTitles = {"지금 뜨는", "최근 올라온", "마감 임박"};

        SortPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return ArticlesFragment.newInstance(pageTitles[i]);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitles[position];
        }
    }
}