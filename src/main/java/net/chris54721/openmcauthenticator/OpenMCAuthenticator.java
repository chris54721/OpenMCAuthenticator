package net.chris54721.openmcauthenticator;

import java.net.Proxy;
import net.chris54721.openmcauthenticator.exceptions.*;
import net.chris54721.openmcauthenticator.responses.AuthenticationResponse;
import net.chris54721.openmcauthenticator.responses.ErrorResponse;
import net.chris54721.openmcauthenticator.responses.RefreshResponse;
import net.chris54721.openmcauthenticator.responses.RequestResponse;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

/**
 * OpenMCAuthenticator - Simple Minecraft authenticator
 *
 * @author Chris54721
 * @version 1.0
 * @see <a href="https://github.com/Chris54721/OpenMCAuthenticator">OpenMCAuthenticator on GitHub</a>
 */
public class OpenMCAuthenticator {

    /**
     * Authenticates an user with an username and a password.
     * Allows to send a custom client token with the request.
     *
     * @param username    The username, can be a nickname (old Minecraft account) or an email (Mojang account)
     * @param password    The password for the account
     * @param clientToken Custom client token to be sent with the request (should be unique)
     * @param proxy       A proxy to use when making the HTTP request
     * @return An AuthenticationResponse containing the server response
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidCredentialsException        bad or empty username/password pair
     * @throws UserMigratedException              email should be used as username instead of nickname
     * @see OpenMCAuthenticator#authenticate(String username, String password)
     * @see OpenMCAuthenticator#authenticate(String username, String password, String clientToken)
     */
    public static AuthenticationResponse authenticate(String username, String password, String clientToken, Proxy proxy) throws RequestException, AuthenticationUnavailableException {
        RequestResponse result = sendJsonPostRequest(getRequestUrl("authenticate"), JsonUtils.credentialsToJson(username, password, clientToken), proxy);
        if(result.isSuccessful()) {
            String accessToken = (String) result.getData().get("accessToken");
            String rClientToken = (String) result.getData().get("clientToken");
            Profile selectedProfile = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData().get("selectedProfile")), Profile.class);
            Profile[] availableProfiles = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData().get("availableProfiles")), Profile[].class);
            return new AuthenticationResponse(accessToken, rClientToken, selectedProfile, availableProfiles);
        } else {
            ErrorResponse errorResponse = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData()), ErrorResponse.class);
            if(result.getData().get("cause") != null && ((String) (result.getData().get("cause"))).equalsIgnoreCase("UserMigratedException"))
                throw new UserMigratedException(errorResponse);
            else throw new InvalidCredentialsException(errorResponse);
        }
    }

    /**
     * Authenticates an user with an username and a password.
     * The server will generate a random client token.
     *
     * @param username The username, can be a nickname (old Minecraft account) or an email (Mojang account)
     * @param password The password for the account
     * @param clientToken Custom client token to be sent with the request (should be unique)
     * @return An AuthenticationResponse containing the server response
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidCredentialsException        bad or empty username/password pair
     * @throws UserMigratedException              email should be used as username instead of nickname
     * @see OpenMCAuthenticator#authenticate(String username, String password, String clientToken, Proxy proxy)
     * @see OpenMCAuthenticator#authenticate(String username, String password)
     */
    public static AuthenticationResponse authenticate(String username, String password, String clientToken) throws RequestException, AuthenticationUnavailableException {
      return authenticate(username, password, clientToken, null);
    }

    /**
     * Authenticates an user with an username and a password.
     * The server will generate a random client token.
     *
     * @param username The username, can be a nickname (old Minecraft account) or an email (Mojang account)
     * @param password The password for the account
     * @return An AuthenticationResponse containing the server response
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidCredentialsException        bad or empty username/password pair
     * @throws UserMigratedException              email should be used as username instead of nickname
     * @see OpenMCAuthenticator#authenticate(String username, String password, String clientToken)
     * @see OpenMCAuthenticator#authenticate(String username, String password, String clientToken, Proxy proxy)
     */
    public static AuthenticationResponse authenticate(String username, String password) throws RequestException, AuthenticationUnavailableException {
        return authenticate(username, password, null, null);
    }

    /**
     * Refreshes the given access token.
     *
     * @param accessToken The authentication token to be refreshed
     * @param clientToken Client token to be sent with the request. <b>Needs to be identical to the one received when getting the token.</b>
     * @param proxy       A proxy to use when making the HTTP request
     * @return A RefreshResponse containing the server response
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidTokenException              the provided token is invalid
     * @see OpenMCAuthenticator#refresh(String accessToken, String clientToken)
     */
    public static RefreshResponse refresh(String accessToken, String clientToken, Proxy proxy) throws RequestException, AuthenticationUnavailableException {
        RequestResponse result = sendJsonPostRequest(getRequestUrl("refresh"), JsonUtils.tokenToJson(accessToken, clientToken), proxy);
        if(result.isSuccessful()) {
            String newAccessToken = (String) result.getData().get("accessToken");
            String rClientToken = (String) result.getData().get("clientToken");
            Profile selectedProfile = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData().get("selectedProfile")), Profile.class);
            return new RefreshResponse(newAccessToken, rClientToken, selectedProfile);
        } else {
            ErrorResponse errorResponse = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData()), ErrorResponse.class);
            throw new InvalidTokenException(errorResponse);
        }
    }

    /**
     * Refreshes the given access token.
     *
     * @param accessToken The authentication token to be refreshed
     * @param clientToken Client token to be sent with the request. <b>Needs to be identical to the one received when getting the token.</b>
     * @return A RefreshResponse containing the server response
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidTokenException              the provided token is invalid
     * @see OpenMCAuthenticator#refresh(String accessToken, String clientToken, Proxy proxy)
     */
    public static RefreshResponse refresh(String accessToken, String clientToken) throws RequestException, AuthenticationUnavailableException {
      return refresh(accessToken, clientToken, null);
    }

    /**
     * Validates the given access token. <b>This will return true only if the token is the most recently generated!</b>
     * Allows to send a custom client token with the request.
     *
     * @param accessToken The authentication token to be validated
     * @param clientToken Custom client token to be sent with the request (should be unique)
     * @param proxy       A proxy to use when making the HTTP request
     * @return true if the token is valid, false otherwise.
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidTokenException              the provided token is invalid
     * @see OpenMCAuthenticator#validate(String accessToken)
     * @see OpenMCAuthenticator#validate(String accessToken, String clientToken)
     */
    public static boolean validate(String accessToken, String clientToken, Proxy proxy) throws RequestException, AuthenticationUnavailableException {
        RequestResponse result = sendJsonPostRequest(getRequestUrl("validate"), JsonUtils.tokenToJson(accessToken, clientToken), proxy);
        if(result.isSuccessful()) {
            return true;
        } else {
            ErrorResponse errorResponse = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData()), ErrorResponse.class);
            throw new InvalidTokenException(errorResponse);
        }
    }

    /**
     * Validates the given access token. <b>This will return true only if the token is the most recently generated!</b>
     * Allows to send a custom client token with the request.
     *
     * @param accessToken The authentication token to be validated
     * @param clientToken Custom client token to be sent with the request (should be unique)
     * @return true if the token is valid, false otherwise.
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidTokenException              the provided token is invalid
     * @see OpenMCAuthenticator#validate(String accessToken)
     * @see OpenMCAuthenticator#validate(String accessToken, String clientToken, Proxy proxy)
     */
    public static boolean validate(String accessToken, String clientToken) throws RequestException, AuthenticationUnavailableException {
      return validate(accessToken, clientToken, null);
    }

    /**
     * Validates the given access token. <b>This will return true only if the token is the most recently generated!</b>
     * The server will generate a random client token.
     *
     * @param accessToken The authentication token to be validated
     * @return true if the token is valid, false otherwise.
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidTokenException              the provided token is invalid
     * @see OpenMCAuthenticator#validate(String accessToken, String clientToken)
     * @see OpenMCAuthenticator#validate(String accessToken, String clientToken, Proxy proxy)
     */
    public static boolean validate(String accessToken) throws RequestException, AuthenticationUnavailableException {
        return validate(accessToken, null, null);
    }

    /**
     * Invalidates the given access token.
     *
     * @param accessToken The authentication token to be validated
     * @param clientToken Client token to be sent with the request. <b>Needs to be identical to the one received when getting the token.</b>
     * @param proxy       A proxy to use when making the HTTP request
     * @return true if the token was invalidated successfully, false otherwise.
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidTokenException              the provided token is invalid
     * @see OpenMCAuthenticator#invalidate(String accessToken, String clientToken)
     */
    public static boolean invalidate(String accessToken, String clientToken, Proxy proxy) throws RequestException, AuthenticationUnavailableException {
        RequestResponse result = sendJsonPostRequest(getRequestUrl("invalidate"), JsonUtils.tokenToJson(accessToken, clientToken), proxy);
        if(result.isSuccessful()) {
            return true;
        } else {
            ErrorResponse errorResponse = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData()), ErrorResponse.class);
            throw new InvalidTokenException(errorResponse);
        }
    }

    /**
     * Invalidates the given access token.
     *
     * @param accessToken The authentication token to be validated
     * @param clientToken Client token to be sent with the request. <b>Needs to be identical to the one received when getting the token.</b>
     * @return true if the token was invalidated successfully, false otherwise.
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidTokenException              the provided token is invalid
     * @see OpenMCAuthenticator#invalidate(String accessToken, String clientToken, Proxy proxy)
     */
    public static boolean invalidate(String accessToken, String clientToken) throws RequestException, AuthenticationUnavailableException {
      return invalidate(accessToken, clientToken, null);
    }

    /**
     * Invalidates <i>every</i> access token for an user, by providing username and password
     * Allows to send a custom client token with the request.
     *
     * @param username    The username, can be a nickname (old Minecraft account) or an email (Mojang account)
     * @param password    The password for the account
     * @param clientToken Custom client token to be sent with the request (should be unique)
     * @param proxy       A proxy to use when making the HTTP request
     * @return true if the signout request was successful, false otherwise.
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidCredentialsException        bad or empty username/password pair
     * @throws UserMigratedException              email should be used as username instead of nickname
     * @see OpenMCAuthenticator#signout(String username, String password)
     */
    public static boolean signout(String username, String password, String clientToken, Proxy proxy) throws RequestException, AuthenticationUnavailableException {
        RequestResponse result = sendJsonPostRequest(getRequestUrl("signout"), JsonUtils.credentialsToJson(username, password, clientToken), proxy);
        if(result.isSuccessful()) {
            return true;
        } else {
            ErrorResponse errorResponse = JsonUtils.gson.fromJson(JsonUtils.gson.toJson(result.getData()), ErrorResponse.class);
            if(result.getData().get("cause") != null && ((String) (result.getData().get("cause"))).equalsIgnoreCase("UserMigratedException"))
                throw new UserMigratedException(errorResponse);
            else throw new InvalidCredentialsException(errorResponse);
        }
    }

    /**
     * Invalidates <i>every</i> access token for an user, by providing username and password
     * Allows to send a custom client token with the request.
     *
     * @param username    The username, can be a nickname (old Minecraft account) or an email (Mojang account)
     * @param password    The password for the account
     * @param clientToken Custom client token to be sent with the request (should be unique)
     * @return true if the signout request was successful, false otherwise.
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidCredentialsException        bad or empty username/password pair
     * @throws UserMigratedException              email should be used as username instead of nickname
     * @see OpenMCAuthenticator#signout(String username, String password)
     * @see OpenMCAuthenticator#signout(String username, String password, String clientToken, Proxy proxy)
     */
    public static boolean signout(String username, String password, String clientToken) throws RequestException, AuthenticationUnavailableException {
      return signout(username, password, clientToken, null);
    }

    /**
     * Invalidates <i>every</i> access token for an user, by providing username and password
     * The server will generate a random client token.
     *
     * @param username The username, can be a nickname (old Minecraft account) or an email (Mojang account)
     * @param password The password for the account
     * @return true if the signout request was successful, false otherwise.
     * @throws AuthenticationUnavailableException the servers are unreachable
     * @throws InvalidCredentialsException        bad or empty username/password pair
     * @throws UserMigratedException              email should be used as username instead of nickname
     * @see OpenMCAuthenticator#signout(String username, String password, String clientToken)
     * @see OpenMCAuthenticator#signout(String username, String password, String clientToken, Proxy proxy)
     */
    public static boolean signout(String username, String password) throws RequestException, AuthenticationUnavailableException {
        return signout(username, password, null, null);
    }

    private static URL getRequestUrl(String request) {
        try {
            return new URL("https://authserver.mojang.com/" + request);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static RequestResponse sendJsonPostRequest(URL requestUrl, String payload, Proxy proxy) throws AuthenticationUnavailableException {
        HttpsURLConnection connection = null;
        try {
            byte[] payloadBytes = payload.getBytes("UTF-8");
            connection = (HttpsURLConnection) (proxy != null ? requestUrl.openConnection(proxy) : requestUrl.openConnection());
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(payloadBytes.length));
            connection.setUseCaches(false);
            OutputStream out = connection.getOutputStream();
            out.write(payloadBytes, 0, payloadBytes.length);
            out.close();

            int responseCode = connection.getResponseCode();
            String line;
            BufferedReader reader = null;
            String response;
            switch(responseCode) {
                case 200:
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    response = reader.readLine();
                    break;
                case 204:
                    response = "";
                    break;
                default:
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8"));
                    response = reader.readLine();
                    break;
            }
            if(reader != null) reader.close();
            Map<String, Object> map = JsonUtils.gson.fromJson(response, JsonUtils.stringObjectMap);
            return new RequestResponse(responseCode, map);
        } catch(Exception e) {
            e.printStackTrace();
            throw new AuthenticationUnavailableException(null);
        } finally {
            if(connection != null) connection.disconnect();
        }
    }

}
