package com.gomsang.lab.publicchain.ui.fragments.navigations;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.FragmentMoreBinding;
import com.gomsang.lab.publicchain.databinding.ItemMenuBinding;
import com.gomsang.lab.publicchain.datas.ChatMessageData;
import com.gomsang.lab.publicchain.datas.UserData;
import com.gomsang.lab.publicchain.libs.Constants;
import com.gomsang.lab.publicchain.libs.PublicChainState;
import com.gomsang.lab.publicchain.libs.RecyclerItemClickListener;
import com.gomsang.lab.publicchain.libs.VerticalSpaceItemDecoration;
import com.gomsang.lab.publicchain.ui.dialogs.CurrentCampaignsDialog;

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

        binding.menuRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), binding.menuRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        UserData userData = PublicChainState.getInstance().getCurrentUserData();
                        AlertDialog.Builder userInfoBuilder = new AlertDialog.Builder(getActivity());
                        userInfoBuilder.setTitle("사용자 정보")
                                .setMessage(Html.fromHtml("<b>사용자 이름</b><br/>" + userData.getName()
                                        + "<br/><br/><b>이메일</b><br/>" + userData.getEmail()
                                        + "<br/><br/><b>휴대폰</b><br/>" + userData.getPhone()
                                        + "<br/><br/><b>주소</b><br/>" + userData.getAddress()))
                                .setPositiveButton("확인", (dialog, id) -> {

                                });
                        userInfoBuilder.create().show();
                        break;

                    case 1:
                        CurrentCampaignsDialog currentCampaignsDialog = new CurrentCampaignsDialog(getActivity(), PublicChainState.getInstance().getCurrentUserData());
                        currentCampaignsDialog.show();
                        break;
                    case 2:
                        AlertDialog.Builder appInfoBuilder = new AlertDialog.Builder(getActivity());
                        appInfoBuilder.setTitle("Public Chain 정보")
                                .setMessage("퍼블릭 체인은 공공데이터를 통한 위치기반 정보 및 의안정보에서 다양한 종류의 서명을 시작," +
                                        " 블록체인 트랜잭션을 결합해 안전하고 무결성을 증명할 수 있는 솔루션 입니다.\n\n" +
                                        "ArbiterLab in ODF 2018")
                                .setPositiveButton("확인", (dialog, id) -> {

                                });
                        appInfoBuilder.create().show();
                        break;
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
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