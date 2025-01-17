package utils;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by priya on 14/08/2014.
 */
public class JsonParser {

    public static JSONArray parseJSONArray(String response) {
        return new JSONArray(response);
    }

    public static JSONObject parseJSONObject(String response) {
        return new JSONObject(response);
    }

    public static String getJsonFromInputStream(URL corpusResource) {
        System.out.println(corpusResource);
        InputStream is = null;

        try {
            is = new FileInputStream(corpusResource.getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String jsonTxt = null;
        try {
            jsonTxt = IOUtils.toString(is);
        } catch (IOException e) {
            System.out.println("Ooops.. unable to parse file");
        }
        return jsonTxt;
    }
}
