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

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.DialogCurrentSignaturesBinding;
import com.gomsang.lab.publicchain.datas.CampaignData;
import com.gomsang.lab.publicchain.datas.SignatureData;
import com.gomsang.lab.publicchain.ui.adapters.SignatureAdpater;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Gyeongrok Kim on 2017-08-15.
 */

public class CurrentSignaturesDialog extends Dialog {

    private Context context;
    private CampaignData campaignData;

    private DialogCurrentSignaturesBinding binding;

    private DatabaseReference database;

    private ArrayList<String> exportLinks = new ArrayList<>();

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

        final SignatureAdpater signatureAdpater = new SignatureAdpater(context);
        binding.signatureList.setAdapter(signatureAdpater);

        database = FirebaseDatabase.getInstance().getReference();
        database.child("signatures").child(campaignData.getUuid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SignatureData signatureData = dataSnapshot.getValue(SignatureData.class);
                signatureAdpater.add(signatureData);
                signatureAdpater.notifyDataSetChanged();

                if (signatureData.getMessage() != null)
                    exportLinks.add(signatureData.getTxid());

                binding.signMontiorTextView.setText("Now " + signatureAdpater.getCount() + " people signed");
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

        binding.exportButton.setOnClickListener((View view) -> {
            ExportDialog exportDialog = new ExportDialog(context, exportLinks);
            exportDialog.show();
        });
    }


    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}
