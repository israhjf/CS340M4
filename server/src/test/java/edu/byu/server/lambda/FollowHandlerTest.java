package edu.byu.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.byu.model.domain.User;
import edu.byu.model.services.request.FollowRequest;
import edu.byu.model.services.response.FollowResponse;
import edu.byu.server.service.FollowServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class FollowHandlerTest {

    FollowRequest request;
    Context context;

    @BeforeEach
    void setUp() {
        User newFollower1 = new User("User1", "Test1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/json/mnames.json");
        User newFollower2 = new User("User2", "Test2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/json/mnames.json");
        request = new FollowRequest(newFollower1, newFollower2);
    }

    @Test
    void handleRequest() {
        FollowHandler followHandler = new FollowHandler();
        FollowResponse response = followHandler.handleRequest(request, this.context);

        assertTrue(response.success);
    }
}