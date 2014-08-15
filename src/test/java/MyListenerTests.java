import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONArray;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Assert;
import org.junit.Test;
import rail.client.DateTimeUtils;
import rail.client.MyListener;
import rail.client.TrainInformation;

import java.util.List;


/**
 * Created by priya on 13/08/2014.
 */
public class MyListenerTests {
    String responseBody = "[{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1407942088000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1407945660000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1407945630000\",\"timetable_variation\":\"2\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"true\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"87911\",\"actual_timestamp\":\"1407945720000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 4\",\"division_code\":\"26\",\"train_terminated\":\"false\",\"train_id\":\"871W92MR13\",\"offroute_ind\":\"false\",\"variation_status\":\"LATE\",\"train_service_code\":\"22711000\",\"toc_id\":\"26\",\"loc_stanox\":\"87911\",\"auto_expected\":\"true\",\"direction_ind\":\"UP\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"87903\",\"line_ind\":\"\"}},{\"header\":{\"msg_type\":\"0003\",\"source_dev_id\":\"\",\"user_id\":\"\",\"original_data_source\":\"SMART\",\"msg_queue_timestamp\":\"1407942090000\",\"source_system_id\":\"TRUST\"},\"body\":{\"event_type\":\"ARRIVAL\",\"gbtt_timestamp\":\"1407945780000\",\"original_loc_stanox\":\"\",\"planned_timestamp\":\"1407945750000\",\"timetable_variation\":\"0\",\"original_loc_timestamp\":\"\",\"current_train_id\":\"\",\"delay_monitoring_point\":\"false\",\"next_report_run_time\":\"1\",\"reporting_stanox\":\"00000\",\"actual_timestamp\":\"1407945720000\",\"correction_ind\":\"false\",\"event_source\":\"AUTOMATIC\",\"train_file_address\":null,\"platform\":\" 2\",\"division_code\":\"26\",\"train_terminated\":\"false\",\"train_id\":\"871T30MO13\",\"offroute_ind\":\"false\",\"variation_status\":\"ON TIME\",\"train_service_code\":\"22711000\",\"toc_id\":\"26\",\"loc_stanox\":\"62203\",\"auto_expected\":\"true\",\"direction_ind\":\"DOWN\",\"route\":\"0\",\"planned_event_type\":\"ARRIVAL\",\"next_report_stanox\":\"62201\",\"line_ind\":\"\"}}]";
    MyListener myListener = new MyListener();
    JSONArray responseJSON = myListener.parseJsonResponse(responseBody);

    @Test
    public void checkNumberOfItems() {
        Assert.assertEquals(2, myListener.numberOfResponses(responseJSON));
    }

    @Test
    public void testResponseParsingTrainInformation() {
        List<TrainInformation> trains = myListener.getTrainsInformation(responseJSON);
        Assert.assertEquals(trains.size(), 2);

        assertThat(trains, hasItems(
                new TrainInformation("871W92MR13", "ARRIVAL", "87911", DateTimeUtils.getTimeFromTimeStamp("1407945630000"), DateTimeUtils.getTimeFromTimeStamp("1407945720000"), "87903", false),
                new TrainInformation("871T30MO13", "ARRIVAL", "62203", DateTimeUtils.getTimeFromTimeStamp("1407945750000"), DateTimeUtils.getTimeFromTimeStamp("1407945720000"), "62201", false)
                ));
    }

    @Test
    public void testTimeStampConversion(){
        String timestamp = "1407955440000";
        DateTime actualDateTimeFromResponse = DateTimeUtils.getTimeFromTimeStamp(timestamp);

        DateTime expectedDateTime = new DateTime(new Long(1407955440000L)).withZone(DateTimeZone.forID("UTC"));
        assertThat(actualDateTimeFromResponse, equalTo(expectedDateTime));
    }

    @Test
    public void testFilterTrainInformation(){
        List<TrainInformation> trains = myListener.getTrainsInformation(responseJSON);
        List<TrainInformation> selectedTrainInfo = myListener.filterByStanoxCode("62", trains);
        assertThat(selectedTrainInfo, hasItem(
                new TrainInformation("871T30MO13", "ARRIVAL", "62203", DateTimeUtils.getTimeFromTimeStamp("1407945750000"), DateTimeUtils.getTimeFromTimeStamp("1407945720000"), "62201", false)
        ));
    }
}
