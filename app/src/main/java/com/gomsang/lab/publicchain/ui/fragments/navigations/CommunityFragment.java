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
import com.gomsang.lab.publicchain.libs.Constants;
import com.gomsang.lab.publicchain.ui.fragments.ArticlesFragment;
import com.gomsang.lab.publicchain.ui.fragments.ChatFragment;

import javax.annotation.Nonnull;

public class CommunityFragment extends Fragment {
    public String parameter;

    public CommunityFragment() {
    }

    public static CommunityFragment newInstance(String parameter) {
        CommunityFragment fragment = new CommunityFragment();
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

        private String[] pageTitles = {"의안 정보", "유저 캠페인", "유저 토론"};

        SortPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i){
                case 0:
                    return ArticlesFragment.newInstance(Constants.BOARDSORT_BILLS);
                case 1:
                    return ArticlesFragment.newInstance(Constants.BOARDSORT_USERCAMPAIGNS);
                case 2:
                    return ChatFragment.newInstance(Constants.CHATCHANNEL_COMMUNITY);

            }
            return null;
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