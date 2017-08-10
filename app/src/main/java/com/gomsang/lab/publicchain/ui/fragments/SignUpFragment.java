package com.gomsang.lab.publicchain.ui.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gomsang.lab.publicchain.R;
import com.gomsang.lab.publicchain.databinding.FragmentSignUpBinding;
import com.gomsang.lab.publicchain.datas.AuthData;
import com.gomsang.lab.publicchain.libs.utils.VerifyUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Gyeongrok Kim on 2017-08-08.
 */

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private DatabaseReference database;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
        binding.setFragment(this);

        database = FirebaseDatabase.getInstance().getReference();
        return binding.getRoot();
    }

    @SuppressLint("RestrictedApi")
    public void progressSignUp(View view) {
        final String name = binding.nameEditText.getText().toString();
        final String email = binding.emailEditText.getText().toString();
        final String phone = binding.phoneNumberEditText.getText().toString();
        final String address = binding.addressEditText.getText().toString();

        if (!VerifyUtil.verifyStrings(name, email, phone, address)) {
            binding.monitorTextView.setText("please fill all fields");
            binding.monitorTextView.setTextColor(Color.RED);
            return;
        }
        binding.monitorTextView.setText("progress...");
        binding.monitorTextView.setTextColor(Color.WHITE);
        binding.confirmButton.setEnabled(false);

        final String identifyToken = VerifyUtil.generateDevicePrivateToken(getActivity());
        AuthData authData = new AuthData(identifyToken, name, email, address, phone);
        database.child("users").child(identifyToken).setValue(authData);

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setCancelable(false);
        alert.setTitle("Copy your private key");
        alert.setMessage("You should copy your private key for login on the other device");

        final EditText uniqueId = new EditText(getActivity());
        uniqueId.setText(authData.getPrivateToken());
        alert.setView(uniqueId, 50, 0, 50, 0);
        alert.setPositiveButton("OK, I COPIED IT", (DialogInterface dialog, int whichButton) -> {
                    getActivity().finish();
                }
        );
        alert.show();
    }
}
