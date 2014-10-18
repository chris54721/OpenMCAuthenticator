#OpenMCAuthenticator

A simple, open and documented Java API for authenticating Minecraft users.
Designed to work with latest Mojang protocols, enables you to parse every bit of data you can request to the auth servers.

##Features
- Works with both Mojang and old Minecraft accounts
- Provides static functions for every request type: `authenticate`, `refresh`, `signout`, `validate` and `invalidate`
- Uses custom exceptions to give you every detail on what went wrong with the request
- Supports custom agents and client tokens

##Download/Installation
Downloads are available on the [releases page](https://github.com/Chris54721/OpenMCAuthenticator/releases).
You can download both a binary .jar to be included in your classpath or the source code, which you can redistribute freely with your project.

**OpenMCAuthenticator requires Google Gson.** Download its latest release and put it in your classpath.

If you downloaded the binary JAR, just add it to your classpath (or extract it to your project's output jar).
If you downloaded the source code, extract the `net` folder in your source folder.

##Usage
The library uses mainly static methods. You won't have to instantiate anything. Just call the static methods in the class `OpenMCAuthenticator` with the required arguments to get your response.

The available methods are: `authenticate`, `refresh`, `signout`, `validate` and `invalidate`.

`authenticate` and `refresh` return a response object extending `LoginResponse` (`AuthenticationResponse` or `RefreshResponse`). This means, regardless of the method used, you will be able to use the following methods:
- `getAccessToken` - returns the access token to be used when launching the game
- `getClientToken` - returns the client token used in the request (defaults to a random UUID)
- `getSelectedProfile` - returns a Profile object representing the current profile

For a more detailed documentation, just check the javadoc included with the library.
###Profiles
The `Profile` object is returned by `loginResponse.getSelectedProfile()` and a `Profile[]` object is returned by `authenticationResponse.getAvailableProfiles()` (where `loginResponse` and `authenticationResponse` are instances of the two classes). It contains the following (non-static) methods:
- `getId()` - returns the user's UUID. Can be converted to a string with `toString()`
- `getName()` - returns the nickname of the user with correct capitalization as a `String`
- `isLegacy()` - returns a boolean; `true` if the profile is a legacy (old) Minecraft account (uses username to log in)

###Exceptions
Every available method returns an exception extending `RequestException` if the request returned an error.
The full list of exceptions is available below.
- `RequestException` - a general exception. Every other exception extends this. To get more detailed info about the exception, call `getError()` or `getErrorMessage()`.
- `AuthenticationUnavailableException` - the Mojang servers are offline and can't be reached. Thrown by every method.
- `InvalidCredentialsException` - the provided credentials are invalid (bad or empty username/password). Thrown by `authenticate` and `signout`.
- `InvalidTokenException` - the provided token is invalid. Thrown by `refresh`, `validate` and `invalidate`.
- `UserMigratedException` - the Mojang account email address should be used as the username instead of the nickname. Thrown by `authenticate` and `signout`.

#### Exceptions example
```
try {
  // make request
} catch(ResponseException e) {
  // use instanceof to determine the exception type
  if(e instanceof AuthenticationUnavailableException) {
    System.out.println("Authentication servers unavailable");
  }
  if(e instanceof InvalidCredentialsException) {
    System.out.println("Bad username or password");
  }
  [...]
}
```

###Authentication
Use `OpenMCAuthenticator.authenticate` to request authentication with an username and a password. 
```
try {
  AuthenticationResponse authResponse = OpenMCAuthenticator.authenticate("username", "password");
  String authToken = authResponse.getAccessToken();
} catch(ResponseException e) {
  // handle exception
}
```

###Refresh
Use `OpenMCAuthenticator.refresh` to request a new token by providing an old (but valid) one. 
```
try {
  RefreshResponse refreshResponse = OpenMCAuthenticator.refresh("old accessToken");
  String authToken = refreshResponse.getAccessToken();
} catch(ResponseException e) {
  // handle exception
}
```

###Validate
Use `OpenMCAuthenticator.validate` to check if the provided access token represents the latest active session.
**This will return true only if the token is the most recently generated.** It's intended to be used by servers to check if the player is not logged in elsewhere with a new access token.
```
try {
  boolean isValid = OpenMCAuthenticator.validate("accessToken");
} catch(ResponseException e) {
  // handle exception
}
```

###Invalidate
Use `OpenMCAuthenticator.invalidate` to invalidate the given access token for the account.
```
try {
  boolean success = OpenMCAuthenticator.invalidate("accessToken");
} catch(ResponseException e) {
  // handle exception
}
```

###Signout
Use `OpenMCAuthenticator.signout` to invalidate *every* access token for an user by providing its username and password.
```
try {
  boolean success = OpenMCAuthenticator.signout("username", "password");
} catch(ResponseException e) {
  // handle exception
}
```

###Using custom client tokens
The `clientToken` value is intended to be generated randomly for every request. OpenMCAuthenticator doesn't send a clientToken by default, as the authentication servers automatically generate one (by default a random UUID, which is then obtainable by the client by calling `getClientToken()` on the returned `LoginResponse`). If for some reason you need to use a custom client token, just add it as a `String` to the request method parameters. For example:
```
// Default (the server will generate the token)
OpenMCAuthenticator.authenticate("username", "password");

// Send a new random UUID generated by the client as the token
OpenMCAuthenticator.authenticate("username", "password", UUID.randomUUID().toString());

// Send a randomly generated secure session identifier (random 32-chars string)
String token = new BigInteger(130, new SecureRandom()).toString(32);
OpenMCAuthenticator.authenticate("username", "password", token);
```

##Reporting issues
If you found an issue in the API, just [click here](https://github.com/Chris54721/OpenMCAuthenticator/issues/new) to report it!
Try to be as accurate as possible and add some steps to reproduce the issue.
