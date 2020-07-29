package edu.byu.model.services.request;

import edu.byu.model.domain.User;

public class FollowRequest {

    public User follower;
    public User newFollowee;

    public FollowRequest() {}

    public FollowRequest(User follower, User newFollowee) {
        this.follower = follower;
        this.newFollowee = newFollowee;
    }

    public User getFollower() {
        return follower;
    }

    public User getNewFollowee() {
        return newFollowee;
    }
}
