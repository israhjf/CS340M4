package edu.byu.server.dao;

import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.response.SignUpResponse;

import static org.junit.Assert.*;

public class UserDAOTest {
    UserDAO userDAO;

    String userAlias;
    String password;
    String firstName;
    String lastName;
    String imageUrl;

    @org.junit.Before
    public void setUp() throws Exception {
        userDAO = new UserDAO();
        userAlias = "@userDao";
        password = "userDao_password";
        firstName = "userDao";
        lastName = "userDao";
        imageUrl = "userDao_imageUrl";
    }

    @org.junit.Test
    public void getSignedUpUserServerResponse() {
        SignUpRequest request = new SignUpRequest(userAlias, password,
                firstName, lastName, imageUrl);

        SignUpResponse response = userDAO.getSignedUpUserServerResponse(request);

        assertNotNull(response);
    }

    @org.junit.Test
    public void getSignedInUserServerResponse() {
    }

    @org.junit.Test
    public void getSignedOutUserServerResponse() {
    }
}