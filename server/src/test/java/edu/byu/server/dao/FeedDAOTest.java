package edu.byu.server.dao;

import org.junit.Before;
import org.junit.Test;

import edu.byu.model.domain.Status;
import edu.byu.model.domain.User;
import edu.byu.model.services.request.FeedRequest;
import edu.byu.model.services.request.FollowRequest;
import edu.byu.model.services.request.StatusRequest;
import edu.byu.model.services.response.FeedResponse;
import edu.byu.model.services.response.FollowResponse;
import edu.byu.model.services.response.StatusResponse;

import static org.junit.Assert.*;

public class FeedDAOTest {
    FeedDAO feedDAO;
    User dummyUser1;
    User dummyUser2;
    Status dummyStatus;

    String userAlias1;
    String userAlias2;
    String password;
    String firstName1;
    String firstName2;
    String lastName;
    String imageUrl;

    @Before
    public void setUp() throws Exception {
        feedDAO = new FeedDAO();
        userAlias1 = "@userDao1";
        userAlias2 = "@userDao2";
        password = "userDao_password";
        firstName1 = "userDao1";
        firstName2 = "userDao2";
        lastName = "userDao";
        imageUrl = "userDao_imageUrl";

        dummyUser1 = new User(firstName1, lastName, userAlias1, imageUrl);
        dummyUser2 = new User(firstName2, lastName, userAlias2, imageUrl);
        dummyStatus = new Status(dummyUser1, "dummyMessage", "dummyTimestamp");

    }

    @Test
    public void getFeedStatuses() {
        FeedRequest request = new FeedRequest(dummyUser1, 10, null);

        FeedResponse response = feedDAO.getFeedStatuses(request);

        assertNotNull(response);
    }

    @Test
    public void submitStatusRequest() {
        StatusRequest request = new StatusRequest(dummyUser1, "dummyMessage", "dummyTimestamp");

        StatusResponse response = feedDAO.submitStatusRequest(request);

        assertNotNull(response);
    }
}