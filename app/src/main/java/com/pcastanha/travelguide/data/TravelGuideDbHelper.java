package com.pcastanha.travelguide.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pcastanha.travelguide.data.TravelGuideContract.GuideEntry;
import com.pcastanha.travelguide.data.TravelGuideContract.LocationEntry;
import com.pcastanha.travelguide.data.TravelGuideContract.ProfileEntry;
import com.pcastanha.travelguide.data.TravelGuideContract.TripEntry;
import com.pcastanha.travelguide.data.TravelGuideContract.GuideItemEntry;

/**
 * Created by pedro.matos.castanha on 6/30/2016.
 */
public class TravelGuideDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "TravelGuide.db";

    public TravelGuideDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table to hold Profiles.
        final String SQL_CREATE_PROFILE_TABLE = "CREATE TABLE " + ProfileEntry.TABLE_NAME + " (" +
                ProfileEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," +
                ProfileEntry.COLUMN_USERNAME + " TEXT NOT NULL, " +
                ProfileEntry.COLUMN_CITIES + " INTEGER, " + // Number of cities visited.
                ProfileEntry.COLUMN_UPCOMING + " INTEGER, " + // Upcoming places to go.
                ProfileEntry.COLUMN_LIKES + " INTEGER, " +
                ProfileEntry.COLUMN_PICTURE + " BLOB, " +
                ProfileEntry.COLUMN_COMPLETE + " REAL " + // World completed percentage.
                " );";

        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + LocationEntry.TABLE_NAME + " (" +

                LocationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE," +

                // the ID of the location entry associated with this weather data
                LocationEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                LocationEntry.COLUMN_LATITUDE + " REAL NOT NULL, " +
                LocationEntry.COLUMN_LONGITUDE + " REAL NOT NULL, " +
                LocationEntry.COLUMN_DESC + " TEXT," +
                LocationEntry.COLUMN_LIKES + " INTEGER, " +

                LocationEntry.COLUMN_COUNTRY + " TEXT, " +
                LocationEntry.COLUMN_CITY + " TEXT, " +
                LocationEntry.COLUMN_PROVINCE + " TEXT " +
                " );";

        final String SQL_CREATE_GUIDE_TABLE = "CREATE TABLE " + GuideEntry.TABLE_NAME + " (" +
                GuideEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," +
                GuideEntry.COLUMN_PROFILE_ID + " INTEGER REFERENCES " + ProfileEntry.TABLE_NAME + " (" + ProfileEntry._ID + ") NOT NULL, " +
                GuideEntry.COLUMN_GUIDENAME + " TEXT NOT NULL, " +
                GuideEntry.COLUMN_LIKES + " INTEGER, " +
                GuideEntry.COLUMN_CREATION_DATE + " DATE NOT NULL " +
                " );";

        final String SQL_CREATE_TRIP_TABLE = "CREATE TABLE " + TripEntry.TABLE_NAME + " (" +
                TripEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," +
                TripEntry.COLUMN_PROFILE_ID + " INTEGER REFERENCES " + ProfileEntry.TABLE_NAME + " (" + ProfileEntry._ID + ") NOT NULL, " +
                TripEntry.COLUMN_GUIDE_ID + " INTEGER REFERENCES " + GuideEntry.TABLE_NAME + " (" + GuideEntry._ID + ") NOT NULL, " +
                TripEntry.COLUMN_TRAVEL_DATE + " DATE NOT NULL " +
                " );";

        final String SQL_CREATE_GUIDE_ITEM_TABLE = "CREATE TABLE " + GuideItemEntry.TABLE_NAME + " (" +
                GuideItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," +
                GuideItemEntry.COLUMN_GUIDE_ID + " INTEGER REFERENCES " + GuideEntry.TABLE_NAME + " (" + GuideEntry._ID + ") NOT NULL, " +
                GuideItemEntry.COLUMN_LOCATION_ID + " INTEGER REFERENCES " + LocationEntry.TABLE_NAME + " (" + LocationEntry._ID + ") NOT NULL " +
                " );";

        /* CREATION SCRIPTS TRANSLATED */
        // CREATE TABLE TB_PROFILE (_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, username_st TEXT NOT NULL, cities_nm INTEGER, upcoming_nm INTEGER, likes_nm INTEGER, picture_img BLOB, wcomplete_dec REAL);
        // CREATE TABLE TB_LOCATION (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, name_st TEXT NOT NULL, lat_db REAL NOT NULL, lon_db REAL NOT NULL, description_st TEXT, likes_nm INTEGER, country_st TEXT, city_st TEXT, province_st TEXT);
        // CREATE TABLE TB_GUIDE (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, cd_seq_creator_profile INTEGER REFERENCES TB_PROFILE (_id) NOT NULL, name_st TEXT NOT NULL, likes_nm INTEGER, creation_date DATE NOT NULL);
        // CREATE TABLE TB_TRIPS (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, cd_seq_profile_id INTEGER REFERENCES TB_PROFILE (_id) NOT NULL, cd_seq_guide_id INTEGER REFERENCES TB_GUIDE (_id) NOT NULL, travel_date DATE NOT NULL);
        // CREATE TABLE TB_GUIDE_ITEM (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, cd_seq_guide_id INTEGER REFERENCES TB_GUIDE (_id) NOT NULL, cd_seq_location_id INTEGER REFERENCES TB_LOCATION (_id) NOT NULL);

        /*// Set up the location column as a foreign key to location table.
        " FOREIGN KEY (" + WeatherEntry.COLUMN_LOC_KEY + ") REFERENCES " +
        LocationEntry.TABLE_NAME + " (" + LocationEntry._ID + "), " +

        // To assure the application have just one weather entry per day
        // per location, it's created a UNIQUE constraint with REPLACE strategy
        " UNIQUE (" + WeatherEntry.COLUMN_DATE + ", " +
        WeatherEntry.COLUMN_LOC_KEY + ") ON CONFLICT REPLACE);";*/

        db.execSQL(SQL_CREATE_PROFILE_TABLE);
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
        db.execSQL(SQL_CREATE_GUIDE_TABLE);
        db.execSQL(SQL_CREATE_TRIP_TABLE);
        db.execSQL(SQL_CREATE_GUIDE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProfileEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GuideEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TripEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GuideItemEntry.TABLE_NAME);
        onCreate(db);
    }
}