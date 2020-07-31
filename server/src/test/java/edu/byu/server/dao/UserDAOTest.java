package edu.byu.server.dao;

import edu.byu.model.domain.User;
import edu.byu.model.services.request.SignInRequest;
import edu.byu.model.services.request.SignOutRequest;
import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.response.SignInResponse;
import edu.byu.model.services.response.SignOutResponse;
import edu.byu.model.services.response.SignUpResponse;

import static org.junit.Assert.*;

public class UserDAOTest {
    UserDAO userDAO;
    User dummyUser;

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
        dummyUser = new User(firstName, lastName, userAlias, imageUrl);
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
        SignInRequest request = new SignInRequest(userAlias, password);

        SignInResponse response = userDAO.getSignedInUserServerResponse(request);

        assertNotNull(response);
    }

    @org.junit.Test
    public void getSignedOutUserServerResponse() {
        SignOutRequest request = new SignOutRequest(dummyUser);

        SignOutResponse response = userDAO.getSignedOutUserServerResponse(request);

        assertNotNull(response);
    }
}