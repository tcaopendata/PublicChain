package com.gomsang.lab.publicchain.libs.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Gyeongrok Kim on 2017-08-08.
 */

public class LocationUtil {
    public static String getAddressInString(Context context, LatLng latLng) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                return convertToString(addresses.get(0));
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String convertToString(Address obj) {
        String add = "";
        if (obj == null)
            return "";
        add = obj.getAddressLine(0);
        /*if (obj.getSubAdminArea() != null)
            add = add + "\n" + obj.getSubAdminArea();
        if (obj.getPostalCode() != null)
            add = add + " - " + obj.getPostalCode();
        if (obj.getAdminArea() != null)
            add = add + "\n" + obj.getAdminArea();
        if (obj.getCountryName() != null)
            add = add + "\n" + obj.getCountryName();*/
        return add;
    }
}
