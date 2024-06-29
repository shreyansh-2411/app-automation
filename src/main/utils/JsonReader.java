package main.utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;

public class JsonReader {

    private JsonReader(){}

    public static JsonObject getJsonData() throws IOException {
        File file = new File(System.getProperty("user.dir")+"/src/test/resources/Testdata/config.json");
        String json = FileUtils.readFileToString(file,"UTF-8");
        Object object = new JsonParser().parse(json);
        return (JsonObject) object;
    }

    public static JsonObject getJsonArray(String key) throws IOException {
        JsonArray jsonArray = getJsonData().getAsJsonArray(key);
        return (JsonObject) jsonArray.asList().get(0);
    }
}
