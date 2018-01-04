package com.gomsang.lab.publicchain.libs.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

/**
 * Created by Gyeongrok Kim on 2017-08-16.
 */

public class LoadUtils {
    public static Bitmap loadResizingSquareDrawable(Context context, String drawableName, int resizedip) {
        Resources resources = context.getResources();
        Bitmap imageBitmap = BitmapFactory.decodeResource(resources, resources.getIdentifier(drawableName, "drawable",
                context.getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, resizedip, resources.getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, resizedip, resources.getDisplayMetrics()), false);
        return resizedBitmap;
    }
}
