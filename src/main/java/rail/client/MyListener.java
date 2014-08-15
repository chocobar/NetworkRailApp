package rail.client;

import net.ser1.stomp.Listener;
import org.joda.time.DateTime;
import org.json.JSONArray;
import rail.trains.TrainInformation;
import utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Example listener process that receives messages
 * in JSON format from the Network Rail ActiveMQ
 *
 * @author Martin.Swanson@blackkitetechnology.com
 */
public class MyListener implements Listener {


    @Override
    public void message(Map header, String body) {
        JSONArray responses = parseJsonResponse(body);
        List<TrainInformation> trainInformations = filterByStanoxCode("54", getTrainsInformation(responses));

        printResponse(trainInformations);
    }

    private void printResponse(List<TrainInformation> trainsInformations) {
        for (TrainInformation train : trainsInformations) {
            System.out.println(
                    train.getTrainEventType() +
                    " - at " + TrainInformation.lookupCurrentStationName(train) +
                    " going to " + TrainInformation.lookupNextStationName(train)
            + train.toString());
        }
    }


    public List<TrainInformation> filterByStanoxCode(String stanoxCode, List<TrainInformation> unfilteredTrainInformation) {
        List<TrainInformation> trainsInfo = new ArrayList<TrainInformation>();
        for (TrainInformation train : unfilteredTrainInformation) {
            if (train.stannoxEquals(stanoxCode)) {
                trainsInfo.add(train);
            }
        }
        return trainsInfo;
    }

    public static JSONArray parseJsonResponse(String response) {
        return new JSONArray(response);
    }

    public List<TrainInformation> getTrainsInformation(JSONArray responses) {
        List<TrainInformation> trainsInfo = new ArrayList<TrainInformation>();
        for (int i = 0; i < responses.length(); i++) {
            String trainID = responses.getJSONObject(i).getJSONObject("body").getString("train_id");
            String eventType = responses.getJSONObject(i).getJSONObject("body").getString("event_type");
            String stannox = responses.getJSONObject(i).getJSONObject("body").getString("loc_stanox");
            String nextStation = responses.getJSONObject(i).getJSONObject("body").getString("next_report_stanox");

            DateTime actualEventTime = DateTimeUtils.getTimeFromTimeStamp(responses.getJSONObject(i).getJSONObject("body").getString("actual_timestamp"));
            DateTime expectedEventTime = DateTimeUtils.getTimeFromTimeStamp(responses.getJSONObject(i).getJSONObject("body").getString("planned_timestamp"));
            boolean terminated = responses.getJSONObject(i).getJSONObject("body").getString("train_terminated").equals("true");
            trainsInfo.add(new TrainInformation(trainID, eventType, stannox, expectedEventTime, actualEventTime, nextStation, terminated));
        }
        return trainsInfo;
    }

    public int numberOfResponses(JSONArray jsonArray) {
        return jsonArray.length();
    }
}
