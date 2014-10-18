package net.openmcauthenticator.exceptions;

import net.openmcauthenticator.responses.ErrorResponse;

/**
 * Main class, extended by every library's exception.
 * @see net.openmcauthenticator.exceptions.AuthenticationUnavailableException
 * @see net.openmcauthenticator.exceptions.InvalidCredentialsException
 * @see net.openmcauthenticator.exceptions.InvalidTokenException
 * @see net.openmcauthenticator.exceptions.UserMigratedException
 */
public class RequestException extends Exception {

    private ErrorResponse error;

    public RequestException(ErrorResponse error) {
        this.error = error;
    }

    public ErrorResponse getResponse() {
        return this.error;
    }

    public String getError() {
        return this.error.getError();
    }

    public String getErrorMessage() {
        return this.error.getErrorMessage();
    }

    public String getErrorCause() {
        return this.error.getCause();
    }

}
