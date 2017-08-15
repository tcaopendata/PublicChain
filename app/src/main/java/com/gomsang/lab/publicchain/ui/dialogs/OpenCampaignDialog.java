package com.gomsang.lab.publicchain.ui.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.DialogOpenCampaignBinding;
import com.gomsang.lab.publicchain.datas.CampaignData;
import com.gomsang.lab.publicchain.libs.utils.LocationUtil;
import com.gomsang.lab.publicchain.libs.utils.VerifyUtil;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;

import java.util.UUID;

/**
 * Created by Gyeongrok Kim on 2017-08-08.
 */

public class OpenCampaignDialog extends Dialog {

    private Context context;
    private String author;

    private DialogOpenCampaignBinding binding;
    private LatLng latLng;

    private DatabaseReference database;
    private StorageReference storage;

    private Uri attachImageUri;

    public OpenCampaignDialog(Context context, String author, LatLng latLng) {
        super(context);
        this.context = context;
        this.latLng = latLng;

        this.database = FirebaseDatabase.getInstance().getReference();
        this.storage = FirebaseStorage.getInstance().getReference();
        this.author = author;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_open_campaign, null, false);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        binding.addressTextView.setText(LocationUtil.getAddressInString(context, latLng));
        binding.fundCampaignCheckBox.setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) -> {
            if (isChecked) {
                binding.goalOfFundEditText.setVisibility(View.VISIBLE);
            } else {
                binding.goalOfFundEditText.setVisibility(View.GONE);
            }
        });
        binding.attachImageButton.setOnClickListener((View view) ->
                RxImagePicker.with(context).requestImage(Sources.GALLERY).subscribe((@NonNull Uri uri) -> {
                    binding.attachImageButton.setText(uri.getLastPathSegment());
                    attachImageUri = uri;
                })
        );
        binding.confirmButton.setOnClickListener(view -> processOpen());
    }

    private void processOpen() {
        boolean verifyStatus = true;

        final String name = binding.nameEditText.getText().toString();
        final String desc = binding.descEditText.getText().toString();
        final String goalOfSign = binding.goalOfSignEditText.getText().toString();

        final boolean isFunding = binding.fundCampaignCheckBox.isChecked();
        final String goalOfFund = binding.goalOfFundEditText.getText().toString();

        if (!VerifyUtil.verifyStrings(name, desc, goalOfSign)) verifyStatus = false;
        if (isFunding) {
            if (!VerifyUtil.verifyString(goalOfFund)) verifyStatus = false;
        }

        if (!verifyStatus) {
            Toast.makeText(context, "please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        CampaignData campaignData = new CampaignData();

        campaignData.setLatitude(latLng.latitude);
        campaignData.setLongitude(latLng.longitude);

        campaignData.setUuid(UUID.randomUUID().toString());
        campaignData.setAuthor(author);

        campaignData.setSignTime(System.currentTimeMillis());

        campaignData.setName(name);
        campaignData.setDesc(desc);
        campaignData.setGoalOfSignature(Integer.parseInt(goalOfSign));

        campaignData.setFunding(isFunding);

        if (isFunding) campaignData.setGoalOfContribution(Double.parseDouble(goalOfFund));

        if (attachImageUri != null) {
            ProgressDialog progressDialog = ProgressDialog.show(context, "Uploading..", "now uploading your attachment");

            StorageReference uploadRef = storage.child("images/" + UUID.randomUUID().toString() + ".jpg");
            UploadTask uploadTask = uploadRef.putFile(attachImageUri);
            uploadTask.addOnFailureListener((@NonNull Exception exception) -> {
                        progressDialog.dismiss();
                        Toast.makeText(context, "occur exception on upload\n" + exception, Toast.LENGTH_SHORT).show();
                    }
            ).addOnSuccessListener((UploadTask.TaskSnapshot taskSnapshot) -> {
                progressDialog.dismiss();
                campaignData.setAttachImage(taskSnapshot.getDownloadUrl().toString());
                database.child("campaigns").child(campaignData.getUuid()).setValue(campaignData);
                dismiss();
            });
        } else {
            database.child("campaigns").child(campaignData.getUuid()).setValue(campaignData);
            dismiss();
        }
    }

    private void setDialogSize(int width, int height) {
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = width;
        params.height = height;
        getWindow().setAttributes(params);
    }

}
