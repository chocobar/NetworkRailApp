package trains;

import rail.trains.StationCodes;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by priya on 14/08/2014.
 */
public class StationLookupTest {

    StationCodes stationCodes = new StationCodes();

    @Test
    public void testLookupCorpusData(){
        String stationAlphaCode = stationCodes.lookupStanoxCode("6221");
        Assert.assertEquals("WESTERTON", stationAlphaCode);
    }

}
