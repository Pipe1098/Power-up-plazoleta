package com.example.foodcourtmicroservice.configuration;

public class Constants {


    private Constants(){ throw new IllegalStateException("Utility class");}

    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully";
    public static final Long OWNER_ROLE_ID = 3L;
    public static final String ROLE_ADMINISTRATOR = "ROLE_ADMINISTRATOR";
    public static final String DIFFERENT_OWNER = "The owner ID does not match the owner ID of this restaurant";
    public static final String ROLE_OWNER = "ROLE_OWNER";
    public static final String EMPLOYEE_ROLE = "EMPLOYEE_ROLE";
    public static final String ROLE_CLIENT = "ROLE_CLIENT";
    public static final String NIT_ALREADY_REGISTERED = "The NIT of this restaurant is already registered.";
    public static final String PROVIDER_DESCRIPTION = "PROVIDER_ROLE";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String CATEGORY_NOT_FOUND = "The category is not registered.";
    public static final String DISH_NOT_FOUND = "There isn't is dishes not available in this moment.";
    public static final String ACTUAL_STATE_NOT_VALID = "Actual state invalid to make the change.";
    public static final String STATE_NOT_VALID = "Not valid State.";
    public static final String DISH_NOT_EXIST= "The dish doesn't exist";
    public static final String ORDER_NOT_EXIST= "The order doesn't exist";
    public static final String DISH_NOT_ACTIVE= "The dish isn't active";
    public static final String DISH_IDRESTAURANT_DIFERENT= "The dish idrestaurant is diferent that the order idrestaurant";
    public static final String IDEMPLOYEE_IDORDER_DIFERENT= "The Employee is diferent that the order employee";
    public static final String NOT_DATA_FOUND_DISH = "No dishes found in this order.";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found for the requested petition";
    public static final String RESTAURANT_NOT_FOUND = "There isn't restaurants in this moment/ user don't have restaurant asociated";
    public static final String STATE_PENDING = "PENDING";
    public static final String STATE_IN_PREPARATION = "IN_PREPARATION";
    public static final String STATE_READY = "READY";
    public static final String STATE_DELIVERED = "DELIVERED";
    public static final String USER_NOT_AUTHENTICATED = "User not authenticated";
    public static final String CLIENT_HAS_AN_ORDER = "Client already has an order";
    public static final String UNAUTHORIZED_USER = "The user is not authorized to perform this request.";
    public static final String MODIFIED_DISH = "The dish has been modified.";
    public static final String ORDER_READY = "The order is ready now";
    public static final String ORDER_DELIVERED = "The order has been delivered";
    public static final String CREATED_DISH = "The dish has been created.";
    public static final String UNREGISTERED_DISH = "The dish you are trying to update is not registered.";
    public static final String USER_PERMISSION_DENIED = "User does not have appropriate permission.";
    public static final String USER_NOT_REGISTRED = "User does not have token.";
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";

}
