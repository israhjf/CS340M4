package edu.byu.model.services.response;
import edu.byu.model.domain.User;

public class SignInResponse extends Response {
    public User user;

    public SignInResponse(String message) {
        super(false);
        this.message = message;
    }

    public SignInResponse(User user) {
        super(true);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
