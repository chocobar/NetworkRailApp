package rail.client;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by priya on 13/08/2014.
 */
public class StationCodes {

//    private static final String stationCorpus = "resources/CORPUSExtract.json";
    private static final URL stationCorpus = StationCodes.class.getClassLoader().getResource("CORPUSExtract.json");


    private final JSONObject stationCodes;

    public StationCodes() {
        stationCodes = getStationData();
    }

    public static JSONObject getStationData() {
        String jsonTxt = JsonParser.getJsonFromInputStream(stationCorpus);
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
