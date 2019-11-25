package com.user.management.app.constant;

public class Resource {

    public static final String AUTHENTICATION = "authentication";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";

    public static final String USER = "user";
    public static final String ALL_USER = "/all";

    public static final String ROL = "rol";

    public static final String REGISTER = "register";
    public static final String COMPLETE_REGISTER = "/complete";
    public static final String VALIDATE = "/validate/{token}";
    public static final String VALIDATE_USER = "/validate/user/{username}";
    public static final String BULK_REGISTER = "/bulk";

    public static final String EXPORT = "export";
    public static final String EXPORT_USER_XSLX = "/users.xlsx";
    public static final String EXPORT_USER_XSL = "/users.xls";
    public static final String EXPORT_USER_CSV = "/users.csv";

}
