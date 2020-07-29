package edu.byu.model.services.request;

import edu.byu.model.domain.Status;
import edu.byu.model.domain.User;

public class StoryRequest {

    public User storyUser;
    public int limit;
    public Status lastStatus;

    public StoryRequest() {}

    public StoryRequest(User storyUser, int limit, Status lastStatus) {
        this.storyUser = storyUser;
        this.limit = limit;
        this.lastStatus = lastStatus;
    }

    public User getStoryUser() {
        return storyUser;
    }

    public int getLimit() {
        return limit;
    }

    public Status getLastStatus() {
        return lastStatus;
    }
}
