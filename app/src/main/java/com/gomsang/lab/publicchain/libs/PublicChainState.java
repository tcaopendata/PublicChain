package com.gomsang.lab.publicchain.libs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.gomsang.lab.publicchain.datas.UserData;
import com.gomsang.lab.publicchain.ui.activities.MainActivity;
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

    public static PublicChainState getInstance() {
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

    public void showTempDialog(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("이더리움 노드에 접속할수 없습니다.\n" +
                "분산 네트워크 ID와 네트워크 RPC 엔드포인트를 체크해주세요")
                .setPositiveButton("확인", (DialogInterface dialog, int id) -> activity.finish());
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
