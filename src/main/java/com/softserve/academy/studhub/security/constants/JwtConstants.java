package com.softserve.academy.studhub.security.constants;

public class JwtConstants {

    public static final String JWT_TYPE = "Bearer ";
    public static final String JWT_HEADER = "Authorization";

    public static final String UNAUTHORISED_JWT = "Unauthorized error. Message - ";
    public static final String CANNOT_SET_USER_JWT = "Can NOT set user authentication -> Message - ";
    public static final String INVALID_JWT_SIGNATURE = "Invalid JWT signature -> Message - ";
    public static final String INVALID_JWT_TOKEN = "Invalid JWT token -> Message - ";
    public static final String JWT_EXPIRED = "Expired JWT token -> Message - ";
    public static final String UNSUPPORTED_JWT = "Unsupported JWT token -> Message - ";
    public static final String JWT_CLAIM_IS_EMPTY = "JWT claims string is empty -> Message - ";

}
