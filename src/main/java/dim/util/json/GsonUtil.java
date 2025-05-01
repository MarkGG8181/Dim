package dim.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;
import java.util.TreeMap;

public class GsonUtil {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static <K, V> JsonObject treeMap(TreeMap<K, V> treeMap) {
        JsonObject jsonObject = new JsonObject();

        for (Map.Entry<K, V> entry : treeMap.entrySet()) {
            String key = String.valueOf(entry.getKey());
            JsonElement tree = GSON.toJsonTree(entry.getValue());
            jsonObject.add(key, tree);
        }

        return jsonObject;
    }

}
