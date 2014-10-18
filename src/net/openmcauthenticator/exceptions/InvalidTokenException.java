package net.openmcauthenticator.exceptions;

import net.openmcauthenticator.responses.ErrorResponse;

/**
 * Thrown when the provided token is invalid or expired.
 */
public class InvalidTokenException extends RequestException {

    public InvalidTokenException(ErrorResponse error) {
        super(error);
    }

}
