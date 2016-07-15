package com.pcastanha.travelguide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by pedro.matos.castanha on 7/6/2016.
 */
public class TravelUtils {

    public static String convertImageToByteArrayString(Bitmap bitmap) {
        String encodedImage;
        byte[] byteArrayImage;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArrayImage = stream.toByteArray();
        encodedImage = Base64.encodeToString(byteArrayImage, Base64.URL_SAFE);

        return encodedImage;
    }

    public static Bitmap convertByteArrayStringToBitmap(String bitmap) {
        Bitmap decodedByte;
        byte[] decodedImage;

        decodedImage = Base64.decode(bitmap, Base64.URL_SAFE);
        decodedByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);

        return decodedByte;
    }
}
