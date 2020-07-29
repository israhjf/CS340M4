package edu.byu.model.services.request;

public class SignInRequest {
    public String userAlias;
    public String password;

    public SignInRequest() {}

    public SignInRequest(String userAlias, String password) {
        this.userAlias = userAlias;
        this.password = password;
    }

    public String getUserAlias() {
        return userAlias;
    }
}

