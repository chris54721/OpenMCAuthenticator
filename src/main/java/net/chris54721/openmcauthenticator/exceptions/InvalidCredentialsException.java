package net.chris54721.openmcauthenticator.exceptions;

import net.chris54721.openmcauthenticator.responses.ErrorResponse;

/**
 * Thrown when the provided username/password pair is wrong or empty.
 */
public class InvalidCredentialsException extends RequestException {

    public InvalidCredentialsException(ErrorResponse error) {
        super(error);
    }

}
