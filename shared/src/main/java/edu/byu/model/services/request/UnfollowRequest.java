package edu.byu.model.services.request;
import edu.byu.model.domain.User;

public class UnfollowRequest {
    public User oldFollowing;
    public User oldFollowee;

    public UnfollowRequest () {}

    public UnfollowRequest(User oldFollowing, User oldFollowee) {
        this.oldFollowing = oldFollowing;
        this.oldFollowee = oldFollowee;
    }

    public User getOldFollowing() {
        return oldFollowing;
    }

    public User getOldFollowee() {
        return oldFollowee;
    }
}
