package net.chris54721.openmcauthenticator.exceptions;

import net.chris54721.openmcauthenticator.responses.ErrorResponse;

/**
 * Thrown when a nickname is used as username instead of an email address and the user has a Mojang account.
 */
public class UserMigratedException extends RequestException {

    public UserMigratedException(ErrorResponse error) {
        super(error);
    }

}
