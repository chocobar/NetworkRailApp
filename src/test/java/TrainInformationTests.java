import rail.client.TrainInformation;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by priya on 14/08/2014.
 */
public class TrainInformationTests {


    @Test
    public void testDeltaPositive() {
        DateTime expectedTime = new DateTime(2014, 12, 12, 12, 12);
        DateTime actualTime = new DateTime(2014, 12, 12, 12, 13);
        Seconds interval = Seconds.seconds(60);

        TrainInformation train1 = new TrainInformation("871T30MO13", "ARRIVAL", "62203", expectedTime, actualTime, "62201", false);

        Assert.assertEquals(interval, train1.timeDelta());
        Assert.assertEquals("Delayed by 1 minute", train1.printStatus());
    }

    @Test
    public void testDeltaNegative() {
        DateTime expectedTime = new DateTime(2014, 12, 12, 12, 14);
        DateTime actualTime = new DateTime(2014, 12, 12, 12, 13);
        Seconds interval = Seconds.seconds(-60);

        TrainInformation train1 = new TrainInformation("871T30MO13", "ARRIVAL", "62203", expectedTime, actualTime, "62201", false);

        Assert.assertEquals(interval, train1.timeDelta());
        Assert.assertEquals("Ahead by 1 minute", train1.printStatus());
    }
}
