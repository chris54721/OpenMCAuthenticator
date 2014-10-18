package net.openmcauthenticator.responses;

import java.util.Map;

/**
 * Response generated after every request made with private method sendJsonPostRequest.
 * Intended for internal use only.
 * @see net.openmcauthenticator.OpenMCAuthenticator#sendJsonPostRequest(java.net.URL, String)
 */
public class RequestResponse {

    private int responseCode = -1;
    private Map<String, Object> data;

    public RequestResponse(int responseCode, Map<String, Object> data) {
        this.responseCode = responseCode;
        this.data = data;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public boolean isSuccessful() {
        return responseCode == 200;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
