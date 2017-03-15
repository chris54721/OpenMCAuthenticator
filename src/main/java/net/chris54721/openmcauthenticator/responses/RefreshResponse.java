package net.chris54721.openmcauthenticator.responses;

import net.chris54721.openmcauthenticator.Profile;

/**
 * Response generated when refresh is successful.
 *
 * @see LoginResponse
 */
public class RefreshResponse extends LoginResponse {

  public RefreshResponse(String accessToken, String clientToken, Profile selectedProfile) {
    super(accessToken, clientToken, selectedProfile);
  }

}
