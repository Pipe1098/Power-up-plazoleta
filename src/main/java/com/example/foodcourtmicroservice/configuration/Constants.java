package com.example.foodcourtmicroservice.configuration;

public class Constants {


    private Constants(){ throw new IllegalStateException("Utility class");}

    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final Long OWNER_ROLE_ID = 3L;
    public static final String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR";
    public static final String DIFFERENT_OWNER = "The owner ID does not match the owner ID of this restaurant";
    public static final String ROLE_OWNER = "ROLE_OWNER";
    public static final String ROLE_CLIENT = "ROLE_CLIENT";
    public static final String NIT_ALREADY_REGISTERED = "The NIT of this restaurant is already registered.";
    public static final String PROVIDER_DESCRIPTION = "PROVIDER_ROLE";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String CATEGORY_NOT_FOUND = "The category is not registered.";
    public static final String UNAUTHORIZED_USER = "The user is not authorized to perform this request.";
    public static final String MODIFIED_DISH = "The dish has been modified.";
    public static final String CREATED_DISH = "The dish has been created.";
    public static final String UNREGISTERED_DISH = "The dish you are trying to update is not registered.";
    public static final String USER_PERMISSION_DENIED = "User does not have appropriate permission.";
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";

}
