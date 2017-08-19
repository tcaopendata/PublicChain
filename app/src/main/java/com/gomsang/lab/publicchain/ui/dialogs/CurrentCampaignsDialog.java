package com.gomsang.lab.publicchain.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.DialogCurrentSignaturesBinding;
import com.gomsang.lab.publicchain.datas.AuthData;
import com.gomsang.lab.publicchain.datas.CampaignData;
import com.gomsang.lab.publicchain.ui.adapters.CampaignAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Gyeongrok Kim on 2017-08-16.
 */

public class CurrentCampaignsDialog extends Dialog {
    private Context context;
    private AuthData currentAuthData;

    private DialogCurrentSignaturesBinding binding;

    private DatabaseReference database;

    public CurrentCampaignsDialog(Context context, AuthData currentAuthData) {
        super(context);
        this.context = context;
        this.currentAuthData = currentAuthData;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_current_signatures, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        final CampaignAdapter campaignAdapter = new CampaignAdapter(context);
        binding.signatureList.setAdapter(campaignAdapter);
        binding.title.setText("Your Campaigns");
        binding.signMontiorTextView.setText("Now " + 0 + " campaign has enrolled");

        database = FirebaseDatabase.getInstance().getReference();

        database.child("campaigns").orderByChild("author").equalTo(currentAuthData.getPublicToken()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                campaignAdapter.add(dataSnapshot.getValue(CampaignData.class));
                campaignAdapter.notifyDataSetChanged();

                binding.signMontiorTextView.setText("Now " + campaignAdapter.getCount() + " campaign has enrolled");
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

        binding.signatureList.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
                    CampaignDialog campaignDialog = new CampaignDialog(context, campaignAdapter.getItem(i), currentAuthData);
                    campaignDialog.show();
                }
        );
    }


    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}
