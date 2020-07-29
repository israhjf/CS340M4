package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.FeedRequest;
import edu.byu.model.services.response.FeedResponse;

import static org.junit.jupiter.api.Assertions.*;

class FeedServiceProxyTest {
    FeedRequest request;

    @BeforeEach
    void setUp(){
        User feedUser = new User("User1", "Test1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/json/mnames.json");
        request = new FeedRequest(feedUser, 10, null);
    }

    @Test
    void getFeedStatuses() {
        FeedServiceProxy feedServiceProxy = new FeedServiceProxy();
        FeedResponse response = null;
        try {
            response = feedServiceProxy.getFeedStatuses(request);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        assertNotNull(response);
    }
}