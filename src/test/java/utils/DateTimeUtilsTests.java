package utils;

import utils.DateTimeUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by priya on 14/08/2014.
 */
public class DateTimeUtilsTests {
    @Test
    public void testPrintDateTime() {
        DateTime expectedTime = new DateTime(1408057320000L).withZone(DateTimeUtils.utc);

        Assert.assertEquals("08/14/2014 23:02:00 ", DateTimeUtils.printLocalDateTime(expectedTime));
    }
}
