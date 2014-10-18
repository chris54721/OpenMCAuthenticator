package net.openmcauthenticator.responses;

import java.io.Serializable;

/**
 * Response generated when an exception is thrown.
 * Intended for internal use only.
 * Call getError() and getErrorMessage() on a RequestException object to get more info about the error.
 * @see net.openmcauthenticator.exceptions.RequestException
 */
public class ErrorResponse implements Serializable {

    private String error;
    private String errorMessage;
    private String cause;

    public String getError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getCause() {
        return cause;
    }

}
