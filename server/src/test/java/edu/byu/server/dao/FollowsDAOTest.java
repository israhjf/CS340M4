package edu.byu.server.dao;

import org.junit.Before;
import org.junit.Test;

import edu.byu.model.domain.Status;
import edu.byu.model.domain.User;
import edu.byu.model.services.request.FollowRequest;
import edu.byu.model.services.request.FollowersRequest;
import edu.byu.model.services.request.FollowingRequest;
import edu.byu.model.services.request.StoryRequest;
import edu.byu.model.services.request.UnfollowRequest;
import edu.byu.model.services.response.FollowResponse;
import edu.byu.model.services.response.FollowersResponse;
import edu.byu.model.services.response.FollowingResponse;
import edu.byu.model.services.response.StoryResponse;
import edu.byu.model.services.response.UnfollowResponse;

import static org.junit.Assert.*;

public class FollowsDAOTest {
    FollowsDAO followsDAO;
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
        followsDAO = new FollowsDAO();
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
    public void addFollow() {
        FollowRequest request = new FollowRequest(dummyUser1, dummyUser2);

        FollowResponse response = followsDAO.addFollow(request);

        assertNotNull(response);
    }

    @Test
    public void submitUnfollowRequest() {
        UnfollowRequest request = new UnfollowRequest(dummyUser1, dummyUser2);

        UnfollowResponse response = followsDAO.submitUnfollowRequest(request);

        assertNotNull(response);
    }

    @Test
    public void getFollowers() {
        FollowersRequest request = new FollowersRequest(dummyUser1, 10, null);

        FollowersResponse response = followsDAO.getFollowers(request);

        assertNotNull(response);
    }

    @Test
    public void getFollowees() {
        FollowingRequest request = new FollowingRequest(dummyUser2, 10, null);

        FollowingResponse response = followsDAO.getFollowees(request);

        assertNotNull(response);
    }
}