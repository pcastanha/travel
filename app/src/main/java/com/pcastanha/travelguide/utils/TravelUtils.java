package com.pcastanha.travelguide.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
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

    /** Determines whether one Location reading is better than the current Location fix
     * @param location  The new Location that you want to evaluate
     * @param currentBestLocation  The current Location fix, to which you want to compare the new one
     */
    public static boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > ConstantsUtil.TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -ConstantsUtil.TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private static boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}
