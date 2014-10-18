package net.openmcauthenticator.exceptions;

import net.openmcauthenticator.responses.ErrorResponse;

/**
 * Thrown when the authentication servers are unreachable.
 */
public class AuthenticationUnavailableException extends RequestException {

    public AuthenticationUnavailableException(ErrorResponse error) {
        super(error);
    }

}
