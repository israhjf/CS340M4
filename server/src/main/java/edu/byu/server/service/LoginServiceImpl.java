package edu.byu.server.service;

import edu.byu.model.domain.AuthToken;
import edu.byu.model.domain.User;
import edu.byu.model.services.LoginService;
import edu.byu.model.services.request.LoginRequest;
import edu.byu.model.services.response.LoginResponse;

public class LoginServiceImpl implements LoginService {

    @Override
    public LoginResponse login(LoginRequest request) {

        // TODO: Generates dummy data. Replace with a real implementation.
        User user = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        return new LoginResponse(user, new AuthToken());
    }
}
