package com.gomsang.lab.publicchain.libs.utils;

/**
 * Created by Gyeongrok Kim on 2017-08-16.
 */

public class LoadUtils {
    /*public static JSONArray getToiletsJsonArray(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.toilets);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("Text Data", byteArrayOutputStream.toString());
        try {
            return new JSONArray(
                    byteArrayOutputStream.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
