package com.gomsang.lab.publicchain.ui.fragments.navigations;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.FragmentMoreBinding;
import com.gomsang.lab.publicchain.databinding.ItemMenuBinding;
import com.gomsang.lab.publicchain.libs.PublicChainState;
import com.gomsang.lab.publicchain.libs.VerticalSpaceItemDecoration;

import java.util.ArrayList;

public class MoreFragment extends Fragment {

    private FragmentMoreBinding binding;

    public MoreFragment() {
        // Required empty public constructor
    }

    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        // Inflate the layout for this fragment
        MoreMenuAdapter moreMenuAdapter = new MoreMenuAdapter(getActivity());
        binding.menuRecyclerView.setAdapter(moreMenuAdapter);
        binding.menuRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.menuRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(16));
        moreMenuAdapter.add(new MoreMenuData("계정정보", R.drawable.ic_account_circle_black_24dp));
        moreMenuAdapter.add(new MoreMenuData("등록한 운동 및 게시글", R.drawable.ic_archive_black_24dp));
        moreMenuAdapter.add(new MoreMenuData("퍼블릭체인 정보", R.drawable.ic_info_outline_black_24dp));
        moreMenuAdapter.notifyDataSetChanged();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (PublicChainState.getInstance().getCurrentUserData() != null) {
            binding.username.setText(PublicChainState.getInstance().getCurrentUserData().getName());
        }
    }

    public class MoreMenuAdapter extends RecyclerView.Adapter<MoreMenuAdapter.ViewHolder> {

        private Context context;
        private ArrayList<MoreMenuData> moreMenuDatas = new ArrayList<>();

        public MoreMenuAdapter(Context context) {
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final ItemMenuBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_menu, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(moreMenuDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return moreMenuDatas.size();
        }

        public void add(MoreMenuData moreMenuData) {
            moreMenuDatas.add(moreMenuData);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private ItemMenuBinding binding;

            public ViewHolder(ItemMenuBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind(MoreMenuData moreMenuData) {
                binding.menuImage.setImageResource(moreMenuData.getMenuImage());
                binding.menuName.setText(moreMenuData.getMenuName());
            }
        }
    }

    public class MoreMenuData {
        private String menuName;
        private int menuImage;


        public MoreMenuData(String menuName, int menuImage) {
            this.menuName = menuName;
            this.menuImage = menuImage;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        public int getMenuImage() {
            return menuImage;
        }

        public void setMenuImage(int menuImage) {
            this.menuImage = menuImage;
        }
    }
}