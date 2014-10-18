package net.openmcauthenticator;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class JsonUtils {

    private static final Map<String, Object> MINECRAFT_AGENT = new HashMap<String, Object>();

    static {
        Map<String, Object> agentValues = new HashMap<String, Object>();
        agentValues.put("name", "Minecraft");
        agentValues.put("version", 1);
        MINECRAFT_AGENT.put("agent", agentValues);
    }

    public static String credentialsToJson(String username, String password, String clientToken) {
        Map<String, Object> jsonData = new HashMap<String, Object>();
        jsonData.putAll(MINECRAFT_AGENT);
        jsonData.put("username", username);
        jsonData.put("password", password);
        if(clientToken != null) jsonData.put("clientToken", clientToken);
        return new Gson().toJson(jsonData);
    }

    public static String tokenToJson(String authToken, String clientToken) {
        Map<String, Object> jsonData = new HashMap<String, Object>();
        jsonData.put("accessToken", authToken);
        if(clientToken != null) jsonData.put("clientToken", clientToken);
        return new Gson().toJson(jsonData);
    }

}
