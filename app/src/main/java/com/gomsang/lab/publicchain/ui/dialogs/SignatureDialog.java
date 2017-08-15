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
import android.widget.Toast;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.DialogSignatureBinding;
import com.gomsang.lab.publicchain.datas.AuthData;
import com.gomsang.lab.publicchain.datas.CampaignData;
import com.gomsang.lab.publicchain.datas.SignatureData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Gyeongrok Kim on 2017-08-09.
 */

public class SignatureDialog extends Dialog {

    private DialogSignatureBinding binding;

    private Context context;
    private CampaignData campaignData;
    private AuthData currentAuthData;

    private DatabaseReference database;

    public SignatureDialog(Context context, CampaignData campaignData, AuthData currentAuthData) {
        super(context);
        this.context = context;
        this.campaignData = campaignData;
        this.currentAuthData = currentAuthData;
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_signature, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        binding.campaignNameTextView.setText("Sign to " + campaignData.getName());
        binding.confirmButton.setOnClickListener(view -> {
            // 후원금 금액을 받아오는 중 예외처리
            final String donationFeeText = binding.donationEditText.getText().toString();
            double donationFee = 0;
            if (donationFeeText != null && donationFeeText.length() != 0) {
                donationFee = Double.parseDouble(donationFeeText);
            }

            SignatureData signatureData = new SignatureData(campaignData.getUuid(),
                    currentAuthData.getPublicToken(), binding.messageEditText.getText().toString(), donationFee);

            database.child("signatures").child(campaignData.getUuid()).push().setValue(signatureData);
            Toast.makeText(context, "signature process complete", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}
