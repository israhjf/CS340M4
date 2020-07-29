package edu.byu.model.services.response;
import edu.byu.model.domain.User;

public class SignOutResponse extends Response{
    public User user;

    public SignOutResponse(String message) {
        super(false);
        this.message = message;
    }

    public SignOutResponse(User user) {
        super(true);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
