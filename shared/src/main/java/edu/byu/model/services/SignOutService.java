package edu.byu.model.services;

import java.io.IOException;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.SignOutRequest;
import edu.byu.model.services.response.SignOutResponse;

public interface SignOutService {
    SignOutResponse getSignOutResponse(SignOutRequest request)
        throws IOException, TweeterRemoteException;
}
//    /**
//     * The singleton instance.
//     */
//    private static SignOutService instance;
//    private final ServerFacade serverFacade;
//
//    public static SignOutService getInstance() {
//        if(instance == null) {
//            instance = new SignOutService();
//        }
//        return instance;
//    }
//
//    private SignOutService() {
//        serverFacade = new ServerFacade();
//    }
//
//    public SignOutResponse getSignOutResponse(SignOutRequest request) {
//        SignOutResponse response = serverFacade.getSignedOutUserServerResponse(request);
//        if (response.getUser() != null)
//            SignInService.getInstance().setCurrentUser(response.getUser());
//        return response;
//    }
//}
