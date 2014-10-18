package net.openmcauthenticator.responses;

import net.openmcauthenticator.Profile;

/**
 * Response generated when refresh is successful.
 * @see net.openmcauthenticator.responses.LoginResponse
 */
public class RefreshResponse extends LoginResponse {

    public RefreshResponse(String accessToken, String clientToken, Profile selectedProfile) {
        super(accessToken, clientToken, selectedProfile);
    }

}
