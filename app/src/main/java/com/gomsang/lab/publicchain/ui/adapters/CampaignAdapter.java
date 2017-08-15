package com.gomsang.lab.publicchain.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.ItemCampaignBinding;
import com.gomsang.lab.publicchain.datas.CampaignData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gyeongrok Kim on 2017-08-16.
 */

public class CampaignAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CampaignData> campaignDatas = new ArrayList<>();

    public CampaignAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return campaignDatas.size();
    }

    @Override
    public CampaignData getItem(int i) {
        return campaignDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemCampaignBinding binding = DataBindingUtil.inflate((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                R.layout.item_campaign, null, false);
        final CampaignData campaignData = campaignDatas.get(i);

        binding.campaignTextView.setText(campaignData.getName());

        final Date signTime = new Date(campaignData.getSignTime());
        final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        binding.dateTextView.setText(sdf.format(signTime));
        return binding.getRoot();
    }

    public void add(CampaignData campaignData){
        campaignDatas.add(campaignData);
    }
}
