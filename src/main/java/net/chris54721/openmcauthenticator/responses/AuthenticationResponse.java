package net.chris54721.openmcauthenticator.responses;

import net.chris54721.openmcauthenticator.Profile;

/**
 * Response generated when authentication is successful.
 *
 * @see LoginResponse
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
