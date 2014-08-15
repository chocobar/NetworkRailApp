package rail.client;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by priya on 13/08/2014.
 */
public class StationCodes {

    private static final String stationCorpus = "/Users/prichand/programming/NetworkRailApp/src/main/resources/CORPUSExtract.json";


    private final JSONObject stationCodes;

    public StationCodes() {
        stationCodes = getStationData();
    }

    public static JSONObject getStationData() {
        String jsonTxt = JsonParser.getJsonFromFile(stationCorpus);
        return new JSONObject(jsonTxt);
    }


    public String lookupStanoxCode(String requiredStanox) {
        if (!requiredStanox.isEmpty()) {
            JSONArray allStations = stationCodes.getJSONArray("TIPLOCDATA");

            for (int i = 0; i < allStations.length(); i++) {
                String stanox = allStations.getJSONObject(i).getString("STANOX");
                if (stanox.equals(requiredStanox)) {
                    return allStations.getJSONObject(i).getString("NLCDESC");
                }
            }
        }
        return "NOT FOUND";
    }

}
