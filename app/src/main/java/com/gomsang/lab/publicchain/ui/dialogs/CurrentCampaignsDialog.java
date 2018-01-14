package com.gomsang.lab.publicchain.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.DialogCurrentCampaignsBinding;
import com.gomsang.lab.publicchain.databinding.DialogCurrentSignaturesBinding;
import com.gomsang.lab.publicchain.datas.CampaignData;
import com.gomsang.lab.publicchain.datas.UserData;
import com.gomsang.lab.publicchain.libs.PublicChainState;
import com.gomsang.lab.publicchain.libs.RecyclerItemClickListener;
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
    private UserData currentUserData;

    private DialogCurrentCampaignsBinding binding;

    private DatabaseReference database;

    public CurrentCampaignsDialog(Context context, UserData currentUserData) {
        super(context);
        this.context = context;
        this.currentUserData = currentUserData;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_current_campaigns, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        /*final CampaignAdapter campaignAdapter = new CampaignAdapter(context);
        binding.signatureList.setAdapter(campaignAdapter);*/
        binding.title.setText("Your Campaigns");
        binding.signMontiorTextView.setText("Now " + 0 + " campaign has enrolled");
        binding.articleRecycler.setLayoutManager(new LinearLayoutManager(context));
        final CampaignAdapter campaignAdapter = new CampaignAdapter(context);
        binding.articleRecycler.setAdapter(campaignAdapter);
        binding.articleRecycler.addOnItemTouchListener(new RecyclerItemClickListener(context, binding.articleRecycler, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CampaignDialog campaignDialog = new CampaignDialog(context, campaignAdapter.getItem(position), PublicChainState
                        .getInstance().getCurrentUserData());
                campaignDialog.show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        database = FirebaseDatabase.getInstance().getReference();

        database.child("campaigns").orderByChild("author").equalTo(currentUserData.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                campaignAdapter.addItem(dataSnapshot.getValue(CampaignData.class));
                campaignAdapter.notifyDataSetChanged();
                binding.signMontiorTextView.setText("Now " + campaignAdapter.getItemCount() + " campaign has enrolled");
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


    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }
}
