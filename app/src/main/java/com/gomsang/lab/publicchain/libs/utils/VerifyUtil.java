package com.gomsang.lab.publicchain.libs.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created by Gyeongrok Kim on 2017-08-08.
 */

public class VerifyUtil {

    // verify string if empty
    public static boolean verifyString(String targetString) {
        if (targetString == null || targetString.length() == 0) return false;
        return true;
    }

    public static boolean verifyStrings(String... targetStrings) {
        for (int ti = 0; ti < targetStrings.length; ti++) {
            if (!verifyString(targetStrings[ti])) return false;
        }
        return true;
    }

    public static boolean verifyStringsFromEditText(EditText... targetEditTexts) {
        for (int ti = 0; ti < targetEditTexts.length; ti++) {
            if (!verifyString(targetEditTexts[ti].getText().toString())) return false;
        }
        return true;
    }

    public static String generateDevicePrivateToken(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(context, "require permission grant", Toast.LENGTH_SHORT).show();
            return null;
        }

        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();
        final String hashed = Hashing.sha256()
                .hashString(deviceId, StandardCharsets.UTF_8)
                .toString();
        return hashed;
    }
}
