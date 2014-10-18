package net.openmcauthenticator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class JsonUtils {

    public static Gson gson;
    public static Type stringObjectMap;
    private static final Map<String, Object> MINECRAFT_AGENT = new LinkedHashMap<String, Object>();

    static {
        gson = new Gson();
        stringObjectMap = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> agentValues = new LinkedHashMap<String, Object>();
        agentValues.put("name", "Minecraft");
        agentValues.put("version", 1);
        MINECRAFT_AGENT.put("agent", agentValues);
    }

    public static String credentialsToJson(String username, String password, String clientToken) {
        Map<String, Object> jsonData = new LinkedHashMap<String, Object>();
        jsonData.putAll(MINECRAFT_AGENT);
        jsonData.put("username", username);
        jsonData.put("password", password);
        if(clientToken != null) jsonData.put("clientToken", clientToken);
        return gson.toJson(jsonData);
    }

    public static String tokenToJson(String authToken, String clientToken) {
        Map<String, Object> jsonData = new LinkedHashMap<String, Object>();
        jsonData.put("accessToken", authToken);
        if(clientToken != null) jsonData.put("clientToken", clientToken);
        return gson.toJson(jsonData);
    }

}
