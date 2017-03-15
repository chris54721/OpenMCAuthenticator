package net.chris54721.openmcauthenticator.responses;

import net.chris54721.openmcauthenticator.Profile;

/**
 * Response generated when authentication/refresh is successful.
 *
 * @see LoginResponse
 */
public class LoginResponse {

  private String accessToken;
  private String clientToken;
  private Profile selectedProfile;

  public LoginResponse(String accessToken, String clientToken, Profile selectedProfile) {
    this.accessToken = accessToken;
    this.clientToken = clientToken;
    this.selectedProfile = selectedProfile;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getClientToken() {
    return clientToken;
  }

  public Profile getSelectedProfile() {
    return selectedProfile;
  }

}
