package com.confiance.common.constants;

public class ErrorConstants {

    // Authentication Errors
    public static final String INVALID_CREDENTIALS = "Invalid email or password";
    public static final String ACCOUNT_DISABLED = "Account is disabled";
    public static final String ACCOUNT_LOCKED = "Account is locked";
    public static final String TOKEN_EXPIRED = "Token has expired";
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String TOKEN_MISSING = "Authorization token is missing";

    // User Errors
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_ALREADY_EXISTS = "User with this email already exists";
    public static final String INVALID_EMAIL = "Invalid email format";
    public static final String WEAK_PASSWORD = "Password does not meet security requirements";

    // Authorization Errors
    public static final String ACCESS_DENIED = "Access denied";
    public static final String INSUFFICIENT_PERMISSIONS = "Insufficient permissions";

    // Validation Errors
    public static final String VALIDATION_ERROR = "Validation error";
    public static final String INVALID_INPUT = "Invalid input data";
    public static final String REQUIRED_FIELD_MISSING = "Required field is missing";

    // Business Logic Errors
    public static final String INSUFFICIENT_BALANCE = "Insufficient account balance";
    public static final String INVALID_TRANSACTION = "Invalid transaction";
    public static final String INVESTMENT_NOT_FOUND = "Investment not found";
    public static final String TRANSACTION_FAILED = "Transaction processing failed";

    // Server Errors
    public static final String INTERNAL_ERROR = "Internal server error occurred";
    public static final String SERVICE_UNAVAILABLE = "Service temporarily unavailable";

    private ErrorConstants() {
        throw new IllegalStateException("Constants class");
    }
}