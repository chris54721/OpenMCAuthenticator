package net.openmcauthenticator.responses;

import net.openmcauthenticator.Profile;

/**
 * Response generated when authentication is successful.
 * @see net.openmcauthenticator.responses.LoginResponse
 */
public class AuthenticationResponse extends LoginResponse {

    private Profile[] availableProfiles;

    public AuthenticationResponse(String accessToken, String clientToken, Profile selectedProfile, Profile[] availableProfiles) {
        super(accessToken, clientToken, selectedProfile);
        this.availableProfiles = availableProfiles;
    }

    public Profile[] getAvailableProfiles() {
        return availableProfiles;
    }

}
