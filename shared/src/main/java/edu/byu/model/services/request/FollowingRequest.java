package edu.byu.model.services.request;

import edu.byu.model.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followees for a specified follower.
 */
public class FollowingRequest {

    public User follower;
    public int limit;
    public User lastFollowee;

    public FollowingRequest() {}
    /**
     * Creates an instance.
     *
     * @param follower the {@link User} whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastFollowee the last followee that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     */
    public FollowingRequest(User follower, int limit, User lastFollowee) {
        this.follower = follower;
        this.limit = limit;
        this.lastFollowee = lastFollowee;
    }

    /**
     * Returns the follower whose followees are to be returned by this request.
     *
     * @return the follower.
     */
    public User getFollower() {
        return follower;
    }

    /**
     * Returns the number representing the maximum number of followees to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Returns the last followee that was returned in the previous request or null if there was no
     * previous request or if no followees were returned in the previous request.
     *
     * @return the last followee.
     */
    public User getLastFollowee() {
        return lastFollowee;
    }
}
