package com.gomsang.lab.publicchain.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.ItemCampaignBinding;
import com.gomsang.lab.publicchain.datas.CampaignData;
import com.gomsang.lab.publicchain.ui.dialogs.CampaignDialog;

import java.util.ArrayList;

/**
 * Created by devkg on 2018-01-14.
 */

public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CampaignData> campaignDatas = new ArrayList<>();

    public CampaignAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ItemCampaignBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_campaign, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(campaignDatas.get(position));
    }

    public CampaignData getItem(int position){
        return campaignDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return campaignDatas.size();
    }

    public void addItem(CampaignData campaignData){
        campaignDatas.add(campaignData);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemCampaignBinding binding;

        public ViewHolder(ItemCampaignBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CampaignData campaignData) {
            binding.campaignTextView.setText(campaignData.getName());
            binding.dateTextView.setText(campaignData.getSignTime() + "");
        }
    }
}
