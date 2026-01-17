package com.confiance.common.constants;

public class ApiConstants {

    // API Version
    public static final String API_VERSION = "/api/v1";

    // Auth Endpoints
    public static final String AUTH_BASE = API_VERSION + "/auth";
    public static final String LOGIN = "/login";
    public static final String REGISTER = "/register";
    public static final String REFRESH = "/refresh";
    public static final String LOGOUT = "/logout";

    // User Endpoints
    public static final String USERS_BASE = API_VERSION + "/users";

    // Investment Endpoints
    public static final String INVESTMENTS_BASE = API_VERSION + "/investments";

    // Transaction Endpoints
    public static final String TRANSACTIONS_BASE = API_VERSION + "/transactions";

    // Portfolio Endpoints
    public static final String PORTFOLIO_BASE = API_VERSION + "/portfolio";

    // Admin Endpoints
    public static final String ADMIN_BASE = API_VERSION + "/admin";

    // Headers
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    // Pagination
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "20";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    private ApiConstants() {
        throw new IllegalStateException("Constants class");
    }
}