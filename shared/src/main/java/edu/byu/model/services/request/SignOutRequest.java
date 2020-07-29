package edu.byu.model.services.request;

import edu.byu.model.domain.User;

public class SignOutRequest {
    public User user;

    public SignOutRequest() {}

    public SignOutRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
