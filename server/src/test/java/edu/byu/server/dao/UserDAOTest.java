package edu.byu.server.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.model.services.request.SignInRequest;
import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.response.SignUpResponse;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {
    UserDAO userDAO;

    String userAlias;
    String password;
    String firstName;
    String lastName;
    String imageUrl;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
        userAlias = "@userDao";
        password = "userDao_password";
        firstName = "userDao";
        lastName = "userDao";
        imageUrl = "userDao_imageUrl";
    }

    @Test
    void getSignedUpUserServerResponse() {
        SignUpRequest request = new SignUpRequest(userAlias, password,
                firstName, lastName, imageUrl);

        SignUpResponse response = userDAO.getSignedUpUserServerResponse(request);

        assertNotNull(response);
    }

    @Test
    void getSignedInUserServerResponse() {
    }

    @Test
    void getSignedOutUserServerResponse() {
    }
}