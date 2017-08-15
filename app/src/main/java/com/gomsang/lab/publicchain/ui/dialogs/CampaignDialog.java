package com.gomsang.lab.publicchain.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.DialogCampaignBinding;
import com.gomsang.lab.publicchain.datas.AuthData;
import com.gomsang.lab.publicchain.datas.CampaignData;
import com.gomsang.lab.publicchain.datas.SignatureData;
import com.gomsang.lab.publicchain.libs.GlideApp;
import com.gomsang.lab.publicchain.libs.utils.LocationUtil;
import com.gomsang.lab.publicchain.ui.activities.AuthActivity;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gyeongrok Kim on 2017-08-09.
 */

public class CampaignDialog extends Dialog {

    private DialogCampaignBinding binding;

    private DatabaseReference database;

    private Context context;
    private CampaignData campaignData;
    private AuthData currentAuthData;

    public CampaignDialog(Context context, CampaignData campaignData, AuthData currentAuthData) {
        super(context);
        this.context = context;
        this.campaignData = campaignData;
        this.currentAuthData = currentAuthData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_campaign, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        database = FirebaseDatabase.getInstance().getReference();

        binding.campaignNameTextView.setText(campaignData.getName());
        binding.addressTextView.setText(LocationUtil.getAddressInString(context, new LatLng(campaignData.getLatitude(),
                campaignData.getLongitude())));
        if (campaignData.getAttachImage() != null) {
            binding.attachImageView.setVisibility(View.VISIBLE);
            GlideApp.with(context).load(campaignData.getAttachImage()).centerCrop().into(binding.attachImageView);
        }

        binding.descTextView.setText(campaignData.getDesc());

        binding.signButton.setOnClickListener(view -> {
            if (currentAuthData != null) {
                SignatureDialog signatureDialog = new SignatureDialog(context, campaignData, currentAuthData);
                signatureDialog.show();
            } else {
                context.startActivity(new Intent(context, AuthActivity.class));
                Toast.makeText(context, "require registration for participate", Toast.LENGTH_SHORT).show();
            }
        });

        // 서명 달성률 표시
        if (campaignData.isFunding()) {
            updateSignProgress(0, campaignData.getGoalOfContribution());
        } else {
            updateSignProgress(0, campaignData.getGoalOfSignature());
        }

        // 서명 실 달성률 (실 서명 개수) 로드
        database.child("signatures").orderByKey().equalTo(campaignData.getUuid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (campaignData.isFunding()) {
                    double fundingsum = 0;
                    for (DataSnapshot signature : dataSnapshot.getChildren()) {
                        fundingsum += signature.child("value").getValue(Double.class);
                    }
                    updateSignProgress(fundingsum, campaignData.getGoalOfContribution());
                } else {
                    updateSignProgress((int) dataSnapshot.getChildrenCount(), campaignData.getGoalOfSignature());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (campaignData.isFunding()) {
                    double fundingsum = 0;
                    for (DataSnapshot signature : dataSnapshot.getChildren()) {
                        fundingsum += signature.child("value").getValue(Double.class);
                    }
                    updateSignProgress(fundingsum, campaignData.getGoalOfContribution());
                } else {
                    updateSignProgress((int) dataSnapshot.getChildrenCount(), campaignData.getGoalOfSignature());
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        database.child("signatures").child(campaignData.getUuid()).orderByChild("signerToken")
                .equalTo(currentAuthData.getPublicToken()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SignatureData signatureData = dataSnapshot.getValue(SignatureData.class);

                Date signTime = new Date(signatureData.getSignTime());
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

                binding.signButton.setEnabled(false);
                binding.signButton.setBackgroundColor(Color.GRAY);
                binding.signButton.setText("signed in " + sdf.format(signTime));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateSignProgress(double progress, double max) {
        if (campaignData.isFunding()) {
            binding.statusTextView.setText("Achieve " + progress +
                    " ETH / " + max + " ETH");
        } else {
            binding.statusTextView.setText("Achieve " + (int) progress +
                    " / " + (int) max);
        }

        binding.statusProgressBar.setProgress((int) progress);
        binding.statusProgressBar.setMax((int) max);
    }


    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}
