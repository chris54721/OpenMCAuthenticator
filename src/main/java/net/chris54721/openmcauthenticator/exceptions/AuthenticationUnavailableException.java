package net.chris54721.openmcauthenticator.exceptions;

import net.chris54721.openmcauthenticator.responses.ErrorResponse;

/**
 * Thrown when the authentication servers are unreachable.
 */
public class AuthenticationUnavailableException extends Exception {

    public AuthenticationUnavailableException(ErrorResponse error) {

    }

}
