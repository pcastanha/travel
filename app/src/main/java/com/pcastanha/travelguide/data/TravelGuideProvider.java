package com.pcastanha.travelguide.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by pedro.matos.castanha on 6/30/2016.
 */
public class TravelGuideProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private TravelGuideDbHelper mOpenHelper;

    static final int PROFILE = 100;
    static final int PROFILE_WITH_ID = 101;
    static final int PROFILE_WITH_ID_AND_TEXT = 102;
    static final int GUIDE = 200;
    static final int GUIDE_WITH_ID = 201;
    static final int LOCATION = 300;
    static final int LOCATION_WITH_ID = 301;
    static final int TRIP = 400;
    static final int TRIP_WITH_ID = 401;
    static final int GUIDE_ITEM = 500;
    static final int GUIDE_ITEM_WITH_ID = 501;

    private static final SQLiteQueryBuilder sQueryBuilder;

    static {
        sQueryBuilder = new SQLiteQueryBuilder();
        sQueryBuilder.setTables(TravelGuideContract.ProfileEntry.TABLE_NAME);

        /*//This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        sWeatherByLocationSettingQueryBuilder.setTables(
                WeatherContract.WeatherEntry.TABLE_NAME + " INNER JOIN " +
                        WeatherContract.LocationEntry.TABLE_NAME +
                        " ON " + WeatherContract.WeatherEntry.TABLE_NAME +
                        "." + WeatherContract.WeatherEntry.COLUMN_LOC_KEY +
                        " = " + WeatherContract.LocationEntry.TABLE_NAME +
                        "." + WeatherContract.LocationEntry._ID);*/
    }

    // This UriMatcher will match each URI to the PROFILE, LOCATION, integer constants defined above.
    // You can test this by uncommenting the testUriMatcher test.
    static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case.
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TravelGuideContract.CONTENT_AUTHORITY;

        // 2) Use the addURI function to match each of the types.  Use the constants from
        // WeatherContract to help define the types to the UriMatcher.
        // The symbol "/*" matches any number while "/#" matches any string.
        matcher.addURI(authority, TravelGuideContract.PATH_PROFILE, PROFILE);
        matcher.addURI(authority, TravelGuideContract.PATH_PROFILE + "/*", PROFILE_WITH_ID);
        matcher.addURI(authority, TravelGuideContract.PATH_PROFILE + "/*/#", PROFILE_WITH_ID_AND_TEXT);

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, TravelGuideContract.PATH_GUIDE, GUIDE);
        matcher.addURI(authority, TravelGuideContract.PATH_GUIDE + "/*", GUIDE_WITH_ID);

        matcher.addURI(authority, TravelGuideContract.PATH_GUIDE_ITEM, GUIDE_ITEM);
        matcher.addURI(authority, TravelGuideContract.PATH_GUIDE_ITEM + "/*", GUIDE_ITEM_WITH_ID);

        matcher.addURI(authority, TravelGuideContract.PATH_TRIPS, TRIP);
        matcher.addURI(authority, TravelGuideContract.PATH_TRIPS + "/*", TRIP_WITH_ID);

        matcher.addURI(authority, TravelGuideContract.PATH_LOCATION, LOCATION);
        matcher.addURI(authority, TravelGuideContract.PATH_LOCATION + "/*", LOCATION_WITH_ID);

        // 3) Return the new matcher
        return matcher;
    }

    //TB_PROFILE._id = ?
    private static final String sProfileIdSelection =
            TravelGuideContract.ProfileEntry.TABLE_NAME + "." + TravelGuideContract.ProfileEntry._ID + " = ? ";

    @Override
    public boolean onCreate() {
        mOpenHelper = new TravelGuideDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Given a URI, will determine what kind of request it is, and query the database accordingly.
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            // "profile/*/*"
            /*case PROFILE_WITH_ID_AND_TEXT: {
                retCursor = getWeatherByLocationSettingAndDate(uri, projection, sortOrder);
                break;
            }*/
            // "profile/*"
            case PROFILE_WITH_ID: {
                retCursor = getProfileById(uri, projection, sortOrder);
                break;
            }
            // "profile"
            case PROFILE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TravelGuideContract.ProfileEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            // "location"
            case LOCATION: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        TravelGuideContract.LocationEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (getContext() != null) {
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        } else {
            throw new NullPointerException("Context is null");
        }
        return retCursor;
    }

    private Cursor getProfileById(Uri uri, String[] projection, String sortOrder) {

        String[] selectionArgs;
        String selection = sProfileIdSelection;
        String profileId = TravelGuideContract.ProfileEntry.getProfileIdFromUri(uri);

        selectionArgs = new String[]{profileId};

        return sQueryBuilder.query(
                mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case PROFILE_WITH_ID_AND_TEXT:
                return TravelGuideContract.ProfileEntry.CONTENT_ITEM_TYPE;
            case PROFILE_WITH_ID:
                return TravelGuideContract.ProfileEntry.CONTENT_ITEM_TYPE;
            case PROFILE:
                return TravelGuideContract.ProfileEntry.CONTENT_TYPE;
            case LOCATION:
                return TravelGuideContract.LocationEntry.CONTENT_TYPE;
            case GUIDE:
                return TravelGuideContract.GuideEntry.CONTENT_TYPE;
            case TRIP:
                return TravelGuideContract.TripEntry.CONTENT_TYPE;
            case GUIDE_ITEM:
                return TravelGuideContract.GuideItemEntry.CONTENT_TYPE; //TODO Finish the remaining content types (_WITH_ID).
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case PROFILE: {
                long _id = db.insert(TravelGuideContract.ProfileEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = TravelGuideContract.ProfileEntry.buildProfileUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case LOCATION: {
                long _id = db.insert(TravelGuideContract.LocationEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = TravelGuideContract.LocationEntry.buildLocationUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case GUIDE: {
                long _id = db.insert(TravelGuideContract.GuideEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = TravelGuideContract.GuideEntry.buildGuideUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TRIP: {
                long _id = db.insert(TravelGuideContract.TripEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = TravelGuideContract.TripEntry.buildTripUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case GUIDE_ITEM: {
                long _id = db.insert(TravelGuideContract.GuideItemEntry.TABLE_NAME, null, contentValues);
                if ( _id > 0 )
                    returnUri = TravelGuideContract.GuideItemEntry.buildGuideItemUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            // Default case points to unknown uri.
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (null != getContext()) {
            getContext().getContentResolver().notifyChange(uri, null);
        } else {
            Log.e(TravelGuideProvider.class.getSimpleName(), "Context is null. Not able to notify changes.");
        }

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @TargetApi(11)
    @Override
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
