package com.gomsang.lab.publicchain.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.DialogCurrentSignaturesBinding;
import com.gomsang.lab.publicchain.datas.CampaignData;

/**
 * Created by Gyeongrok Kim on 2017-08-15.
 */

public class CurrentSignaturesDialog extends Dialog {

    private Context context;
    private CampaignData campaignData;

    private DialogCurrentSignaturesBinding binding;

    public CurrentSignaturesDialog(Context context, CampaignData campaignData) {
        super(context);
        this.context = context;
        this.campaignData = campaignData;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_current_signatures, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


    }


    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}
