package edu.byu.server.dao;

import org.junit.Before;
import org.junit.Test;

import edu.byu.model.domain.Status;
import edu.byu.model.domain.User;
import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.request.StatusRequest;
import edu.byu.model.services.request.StoryRequest;
import edu.byu.model.services.response.SignUpResponse;
import edu.byu.model.services.response.StatusResponse;
import edu.byu.model.services.response.StoryResponse;

import static org.junit.Assert.*;

public class StoryDAOTest {
    StoryDAO storyDAO;
    User dummyUser;
    Status dummyStatus;

    String userAlias;
    String password;
    String firstName;
    String lastName;
    String imageUrl;

    @Before
    public void setUp() throws Exception {
        storyDAO = new StoryDAO();
        userAlias = "@userDao";
        password = "userDao_password";
        firstName = "userDao";
        lastName = "userDao";
        imageUrl = "userDao_imageUrl";
        dummyUser = new User(firstName, lastName, userAlias, imageUrl);
        dummyStatus = new Status(dummyUser, "dummyMessage", "dummyTimestamp");

    }

    @Test
    public void getStoryStatuses() {
        StoryRequest request = new StoryRequest(dummyUser, 10, null);

        StoryResponse response = storyDAO.getStoryStatuses(request);

        assertNotNull(response);
    }

    @Test
    public void submitStatusRequest() {
        StatusRequest request = new StatusRequest(dummyUser, "dummyMessage",
                "dummyTimestamp");

        StatusResponse response = storyDAO.submitStatusRequest(request);

        assertNotNull(response);
    }
}