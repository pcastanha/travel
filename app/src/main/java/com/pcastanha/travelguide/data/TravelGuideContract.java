package com.pcastanha.travelguide.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by pedro.matos.castanha on 6/30/2016.
 */
public class TravelGuideContract {

    /*Global stuff here*/

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.pcastanha.travelguide";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.pcastanha.travelguide/tb_profile/ is a valid path for
    // looking at profile data. content://com.pcastanha.travelguide/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    public static final String PATH_PROFILE = "TB_PROFILE";
    public static final String PATH_LOCATION = "TB_LOCATION";
    public static final String PATH_GUIDE = "TB_GUIDE";
    public static final String PATH_TRIPS = "TB_TRIPS";
    public static final String PATH_GUIDE_ITEM = "TB_GUIDE_ITEM";

    public static final class ProfileEntry implements BaseColumns {

        public static final String TABLE_NAME = "TB_PROFILE";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROFILE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROFILE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PROFILE;

        public static final String COLUMN_USERNAME =  "username_st";
        public static final String COLUMN_CITIES = "cities_nm";
        public static final String COLUMN_UPCOMING = "upcoming_nm";
        public static final String COLUMN_LIKES = "likes_nm";
        public static final String COLUMN_PICTURE = "picture_img";
        public static final String COLUMN_COMPLETE = "wcomplete_dec";

        public static Uri buildProfileUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getProfileIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class LocationEntry implements BaseColumns {

        public static final String TABLE_NAME = "TB_LOCATION";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;

        public static final String COLUMN_NAME =  "name_st";
        public static final String COLUMN_LATITUDE = "lat_db";
        public static final String COLUMN_LONGITUDE = "lon_db";
        public static final String COLUMN_DESC = "description_st";
        public static final String COLUMN_LIKES = "likes_nm";
        public static final String COLUMN_COUNTRY = "country_st";
        public static final String COLUMN_CITY = "city_st";
        public static final String COLUMN_PROVINCE = "province_st";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getLocationIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class GuideEntry implements BaseColumns {

        public static final String TABLE_NAME = "TB_GUIDE";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_GUIDE).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GUIDE;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GUIDE;

        public static final String COLUMN_GUIDENAME =  "name_st";
        public static final String COLUMN_PROFILE_ID = "cd_seq_creator_profile";
        public static final String COLUMN_LIKES = "likes_nm";
        public static final String COLUMN_CREATION_DATE = "creation_date";

        public static Uri buildGuideUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getGuideIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class TripEntry implements BaseColumns {

        public static final String TABLE_NAME = "TB_TRIPS";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRIPS).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRIPS;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRIPS;

        public static final String COLUMN_PROFILE_ID = "cd_seq_profile_id";
        public static final String COLUMN_GUIDE_ID = "cd_seq_guide_id";
        public static final String COLUMN_TRAVEL_DATE = "travel_date";

        public static Uri buildTripUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getTripIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static final class GuideItemEntry implements BaseColumns {

        public static final String TABLE_NAME = "TB_GUIDE_ITEM";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_GUIDE_ITEM).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GUIDE_ITEM;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GUIDE_ITEM;

        public static final String COLUMN_GUIDE_ID = "cd_seq_guide_id";
        public static final String COLUMN_LOCATION_ID = "cd_seq_location_id";

        public static Uri buildGuideItemUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getGuideItemIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }
}