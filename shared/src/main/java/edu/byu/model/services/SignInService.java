package edu.byu.model.services;

import java.io.IOException;

import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.SignInRequest;
import edu.byu.model.services.response.SignInResponse;

public interface SignInService {
    SignInResponse getSignInResponse(SignInRequest request)
        throws IOException, TweeterRemoteException;
}
//    /**
//     * The singleton instance.
//     */
//    private static SignInService instance;
//    private final ServerFacade serverFacade;
//    private User currentUser;
//
//    public static SignInService getInstance() {
//        if(instance == null) {
//            instance = new SignInService();
//        }
//        return instance;
//    }
//
//    private SignInService() {
//        serverFacade = new ServerFacade();
//    }
//
//    public SignInResponse getSignInResponse(SignInRequest request) {
//        SignInResponse response = serverFacade.getSignedInUserServerResponse(request);
//        if (response.getUser() != null)
//            setCurrentUser(response.getUser());
//        return response;
//    }
//
//    public void setCurrentUser(User user){
//        this.currentUser = user;
//    }
//
//    public User getCurrentUser() {
//        return currentUser;
//    }
//}
