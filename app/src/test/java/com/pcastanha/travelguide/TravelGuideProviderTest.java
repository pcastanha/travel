package com.pcastanha.travelguide;

import com.pcastanha.travelguide.activities.LocationsActivity;
import com.pcastanha.travelguide.data.TravelGuideContract;
import com.pcastanha.travelguide.data.TravelGuideDbHelper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by pedro.matos.castanha on 7/1/2016.
 */
public class TravelGuideProviderTest {

    @Test
    public void testBuildUriMatcher() throws Exception {
        assertEquals(1,1);
    }

    @Test
    public void testQuery() throws Exception {
        TravelGuideDbHelper db = new TravelGuideDbHelper(new LocationsActivity());
        db.getReadableDatabase().query(
                TravelGuideContract.ProfileEntry.TABLE_NAME,
                new String[]{"A","B"},
                "TB_PROFILE._id = ?",
                new String[]{"1"},
                null,
                null,
                "ASC");
    }

    @Test
    public void testGetType() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {

    }
}