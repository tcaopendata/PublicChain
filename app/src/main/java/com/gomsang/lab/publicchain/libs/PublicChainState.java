package com.gomsang.lab.publicchain.libs;

import android.app.Activity;
import android.util.Log;

import com.gomsang.lab.publicchain.datas.UserData;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import javax.security.auth.callback.Callback;

/**
 * Created by devkg on 2018-01-02.
 */

public class PublicChainState {

    private UserData currentUserData;
    private static PublicChainState publicChainState = new PublicChainState();

    private FirebaseAuth auth;

    public static PublicChainState getInstatnce() {
        return publicChainState;
    }

    private PublicChainState() {
    }

    public void updateUserData(UserData userData) {
        this.currentUserData = userData;
    }

    public UserData getCurrentUserData() {
        return currentUserData;
    }
}
