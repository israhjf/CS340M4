package edu.byu.model.services.request;

import edu.byu.model.domain.Status;
import edu.byu.model.domain.User;

public class FeedRequest {

    public User feedUser;
    public int limit;
    public Status lastStatus;

    public FeedRequest() {}

    public FeedRequest(User feedUser, int limit, Status lastStatus) {
        this.feedUser = feedUser;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public User getFeedUser() {
        return feedUser;
    }

    public int getLimit() {
        return limit;
    }

    public Status getLastStatus() {
        return lastStatus;
    }
}
