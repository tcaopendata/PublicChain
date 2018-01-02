package com.gomsang.lab.publicchain.ui.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.DialogInputDetailBinding;
import com.gomsang.lab.publicchain.datas.UserData;
import com.gomsang.lab.publicchain.libs.utils.VerifyUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by devkg on 2018-01-02.
 */

public class InputDetailDialog extends Dialog {

    private DialogInputDetailBinding binding;
    private Context context;

    private DatabaseReference database;
    private FirebaseAuth auth;


    public InputDetailDialog(@NonNull Context context) {
        super(context);
        this.context = context;

        database = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_input_detail, null, false);
        binding.setDialog(this);
        setContentView(binding.getRoot());
        setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null) return;
        binding.phoneNumberEditText.setText(firebaseUser.getPhoneNumber());
        binding.phoneNumberEditText.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(context, "you can use our apps after registering", Toast.LENGTH_LONG).show();
        ((Activity) context).finish();
    }

    public void progressSignUp(View view) {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser == null) return;

        final String name = binding.nameEditText.getText().toString();
        final String email = binding.emailEditText.getText().toString();
        final String phone = binding.phoneNumberEditText.getText().toString();
        final String address = binding.addressEditText.getText().toString();

        if (!VerifyUtil.verifyStrings(name, email, phone, address)) {
            binding.monitorTextView.setText(R.string.msg_fields_not_filled);
            binding.monitorTextView.setTextColor(Color.RED);
            return;
        }
        binding.monitorTextView.setText("progress...");
        binding.monitorTextView.setTextColor(Color.WHITE);
        binding.confirmButton.setEnabled(false);

        final UserData userData = new UserData(firebaseUser.getUid(), name, email, address, phone);

        ProgressDialog progressDialog = ProgressDialog.show(context, "Registering..", "Please wait...");
        database.child("users").child(firebaseUser.getUid()).setValue(userData).addOnCompleteListener((@NonNull Task<Void> task) -> {
            progressDialog.dismiss();
            if (task.isComplete() && task.isSuccessful()) {
                InputDetailDialog.this.dismiss();
            } else {
                Toast.makeText(context, "occur unexpected error on registering", Toast.LENGTH_LONG).show();
                ((Activity) context).finish();
                InputDetailDialog.this.dismiss();
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
