package com.hutech.easylearning.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_EXISTED(409, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(404, "User not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(401, "Unauthorized: Incorrect username or password", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(11111  , "UNAUTHORIZED:You do not permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST)
    ;


    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}

