package edu.byu.server.dao;

import edu.byu.model.domain.User;
import edu.byu.model.services.SignInService;
import edu.byu.model.services.request.SignOutRequest;
import edu.byu.model.services.response.SignOutResponse;

public class SignOutDAO {
    /***
     * SignOut Feature
     * @param request
     * @return
     */
    public SignOutResponse getSignedOutUserServerResponse(SignOutRequest request){
        SignOutResponse response;

        User currentUser = new User("Test", "User",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        response = new SignOutResponse(currentUser);
        return response;
    }
}
