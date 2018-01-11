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
import com.gomsang.lab.publicchain.datas.UserData;
import com.gomsang.lab.publicchain.datas.CampaignData;
import com.gomsang.lab.publicchain.datas.DestinationData;
import com.gomsang.lab.publicchain.datas.SignatureData;
import com.gomsang.lab.publicchain.libs.blockchain.BlockChainFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gyeongrok Kim on 2017-08-09.
 */

public class SignatureDialog extends Dialog {

    private DialogSignatureBinding binding;

    private Context context;
    private CampaignData campaignData;
    private UserData currentUserData;

    private DatabaseReference database;

    public SignatureDialog(Context context, CampaignData campaignData, UserData currentUserData) {
        super(context);
        this.context = context;
        this.campaignData = campaignData;
        this.currentUserData = currentUserData;
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

            final SignatureData signatureData = new SignatureData(campaignData.getUuid(),
                    currentUserData.getUid(), binding.messageEditText.getText().toString(), donationFee);

            database.child("currentDest").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //
                    DestinationData destinationData = dataSnapshot.getValue(DestinationData.class);
                    signatureData.setTxid("");
                    database.child("signatures").child(campaignData.getUuid()).push().setValue(signatureData);
                    Toast.makeText(context, "complete\ntxid : " + signatureData.getTxid(), Toast.LENGTH_SHORT).show();
                    dismiss();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        });
    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}
