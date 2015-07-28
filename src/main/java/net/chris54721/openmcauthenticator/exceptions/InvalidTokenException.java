package net.chris54721.openmcauthenticator.exceptions;

import net.chris54721.openmcauthenticator.responses.ErrorResponse;

/**
 * Thrown when the provided token is invalid or expired.
 */
public class InvalidTokenException extends RequestException {

    public InvalidTokenException(ErrorResponse error) {
        super(error);
    }

}
