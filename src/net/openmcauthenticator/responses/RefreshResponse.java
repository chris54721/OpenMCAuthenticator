package net.openmcauthenticator.responses;

import net.openmcauthenticator.Profile;

public class RefreshResponse extends LoginResponse {

    public RefreshResponse(String accessToken, String clientToken, Profile selectedProfile) {
        super(accessToken, clientToken, selectedProfile);
    }

}
