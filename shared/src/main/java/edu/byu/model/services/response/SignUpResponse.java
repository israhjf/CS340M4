package edu.byu.model.services.response;
import edu.byu.model.domain.User;

public class SignUpResponse extends Response{
    public User user;

    public SignUpResponse(String message) {
        super(false);
        this.message = message;
    }

    public SignUpResponse(User user) {
        super(true);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
