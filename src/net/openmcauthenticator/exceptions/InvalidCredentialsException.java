package net.openmcauthenticator.exceptions;

import net.openmcauthenticator.responses.ErrorResponse;

/**
 * Thrown when the provided username/password pair is wrong or empty.
 */
public class InvalidCredentialsException extends RequestException {

    public InvalidCredentialsException(ErrorResponse error) {
        super(error);
    }

}
