package net.openmcauthenticator;

import net.openmcauthenticator.exceptions.RequestException;
import net.openmcauthenticator.responses.AuthenticationResponse;
import net.openmcauthenticator.responses.RefreshResponse;

/**
 * OpenMCAuthenticator - Simple Minecraft authenticator
 * @author Chris54721
 * @version 1.0
 * @see <a href="https://github.com/Chris54721/OpenMCAuthenticator">OpenMCAuthenticator on GitHub</a>
 */
public class OpenMCAuthenticator {

    /**
     * Authenticates an user with an username and a password.
     * Allows to send a custom client token with the request.
     * @param username The username, can be a nickname (old Minecraft account) or an email (Mojang account)
     * @param password The password for the account
     * @param clientToken Custom client token to be sent with the request (should be unique)
     * @see net.openmcauthenticator.OpenMCAuthenticator#authenticate(String username, String password)
     * @throws net.openmcauthenticator.exceptions.AuthenticationUnavailableException the servers are unreachable
     * @throws net.openmcauthenticator.exceptions.InvalidCredentialsException bad or empty username/password pair
     * @throws net.openmcauthenticator.exceptions.UserMigratedException email should be used as username instead of nickname
     * @return An AuthenticationResponse containing the server response
     */
    public static AuthenticationResponse authenticate(String username, String password, String clientToken) throws RequestException {
        return new AuthenticationResponse();
    }

    /**
     * Authenticates an user with an username and a password.
     * The server will generate a random client token.
     * @param username The username, can be a nickname (old Minecraft account) or an email (Mojang account)
     * @param password The password for the account
     * @see net.openmcauthenticator.OpenMCAuthenticator#authenticate(String username, String password, String clientToken)
     * @throws net.openmcauthenticator.exceptions.AuthenticationUnavailableException the servers are unreachable
     * @throws net.openmcauthenticator.exceptions.InvalidCredentialsException bad or empty username/password pair
     * @throws net.openmcauthenticator.exceptions.UserMigratedException email should be used as username instead of nickname
     * @return An AuthenticationResponse containing the server response
     */
    public static AuthenticationResponse authenticate(String username, String password) throws RequestException {
        return authenticate(username, password, null);
    }

    /**
     * Refreshes the given access token.
     * Allows to send a custom client token with the request.
     * @param accessToken The authentication token to be refreshed
     * @param clientToken Custom client token to be sent with the request (should be unique)
     * @see net.openmcauthenticator.OpenMCAuthenticator#refresh(String accessToken)
     * @throws net.openmcauthenticator.exceptions.AuthenticationUnavailableException the servers are unreachable
     * @throws net.openmcauthenticator.exceptions.InvalidTokenException the provided token is invalid
     * @return A RefreshResponse containing the server response
     */
    public static RefreshResponse refresh(String accessToken, String clientToken) throws RequestException {
        return new RefreshResponse();
    }

    /**
     * Refreshes the given access token.
     * The server will generate a random client token.
     * @param accessToken The authentication token to be refreshed
     * @see net.openmcauthenticator.OpenMCAuthenticator#refresh(String accessToken, String clientToken)
     * @throws net.openmcauthenticator.exceptions.AuthenticationUnavailableException the servers are unreachable
     * @throws net.openmcauthenticator.exceptions.InvalidTokenException the provided token is invalid
     * @return A RefreshResponse containing the server response
     */
    public static RefreshResponse refresh(String accessToken) throws RequestException {
        return refresh(accessToken, null);
    }

    /**
     * Validates the given access token. <b>This will return true only if the token is the most recently generated!</b>
     * Allows to send a custom client token with the request.
     * @param accessToken The authentication token to be validated
     * @param clientToken Custom client token to be sent with the request (should be unique)
     * @see net.openmcauthenticator.OpenMCAuthenticator#validate(String accessToken)
     * @throws net.openmcauthenticator.exceptions.AuthenticationUnavailableException the servers are unreachable
     * @throws net.openmcauthenticator.exceptions.InvalidTokenException the provided token is invalid
     * @return true if the token is valid, false otherwise.
     */
    public static boolean validate(String accessToken, String clientToken) throws RequestException {
        return false;
    }

    /**
     * Validates the given access token. <b>This will return true only if the token is the most recently generated!</b>
     * The server will generate a random client token.
     * @param accessToken The authentication token to be validated
     * @see net.openmcauthenticator.OpenMCAuthenticator#validate(String accessToken, String clientToken)
     * @throws net.openmcauthenticator.exceptions.AuthenticationUnavailableException the servers are unreachable
     * @throws net.openmcauthenticator.exceptions.InvalidTokenException the provided token is invalid
     * @return true if the token is valid, false otherwise.
     */
    public static boolean validate(String accessToken) throws RequestException {
        return validate(accessToken, null);
    }

    /**
     * Invalidates the given access token.
     * Allows to send a custom client token with the request.
     * @param accessToken The authentication token to be validated
     * @param clientToken Custom client token to be sent with the request (should be unique)
     * @see net.openmcauthenticator.OpenMCAuthenticator#invalidate(String accessToken)
     * @throws net.openmcauthenticator.exceptions.AuthenticationUnavailableException the servers are unreachable
     * @throws net.openmcauthenticator.exceptions.InvalidTokenException the provided token is invalid
     * @return true if the token was invalidated successfully, false otherwise.
     */
    public static boolean invalidate(String accessToken, String clientToken) throws RequestException {
        return false;
    }

    /**
     * Invalidates the given access token.
     * The server will generate a random client token.
     * @param accessToken The authentication token to be validated
     * @see net.openmcauthenticator.OpenMCAuthenticator#invalidate(String accessToken, String clientToken)
     * @throws net.openmcauthenticator.exceptions.AuthenticationUnavailableException the servers are unreachable
     * @throws net.openmcauthenticator.exceptions.InvalidTokenException the provided token is invalid
     * @return true if the token was invalidated successfully, false otherwise.
     */
    public static boolean invalidate(String accessToken) throws RequestException {
        return invalidate(accessToken, null);
    }

    /**
     * Invalidates <i>every</i> access token for an user, by providing username and password
     * Allows to send a custom client token with the request.
     * @param username The username, can be a nickname (old Minecraft account) or an email (Mojang account)
     * @param password The password for the account
     * @param clientToken Custom client token to be sent with the request (should be unique)
     * @see net.openmcauthenticator.OpenMCAuthenticator#signout(String username, String password)
     * @throws net.openmcauthenticator.exceptions.AuthenticationUnavailableException the servers are unreachable
     * @throws net.openmcauthenticator.exceptions.InvalidCredentialsException bad or empty username/password pair
     * @throws net.openmcauthenticator.exceptions.UserMigratedException email should be used as username instead of nickname
     * @return true if the signout request was successful, false otherwise.
     */
    public static boolean signout(String username, String password, String clientToken) throws RequestException {
        return false;
    }

    /**
     * Invalidates <i>every</i> access token for an user, by providing username and password
     * The server will generate a random client token.
     * @param username The username, can be a nickname (old Minecraft account) or an email (Mojang account)
     * @param password The password for the account
     * @see net.openmcauthenticator.OpenMCAuthenticator#signout(String username, String password, String clientToken)
     * @throws net.openmcauthenticator.exceptions.AuthenticationUnavailableException the servers are unreachable
     * @throws net.openmcauthenticator.exceptions.InvalidCredentialsException bad or empty username/password pair
     * @throws net.openmcauthenticator.exceptions.UserMigratedException email should be used as username instead of nickname
     * @return true if the signout request was successful, false otherwise.
     */
    public static boolean signout(String username, String password) throws RequestException {
        return signout(username, password, null);
    }

}
