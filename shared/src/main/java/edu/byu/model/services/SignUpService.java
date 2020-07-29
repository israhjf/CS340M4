package edu.byu.model.services;

import java.io.IOException;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.response.SignUpResponse;

public interface SignUpService {
    SignUpResponse getSignUpResponse(SignUpRequest request)
        throws IOException, TweeterRemoteException;
}
//    /**
//     * The singleton instance.
//     */
//    private static SignUpService instance;
//    private final ServerFacade serverFacade;
//
//    public static SignUpService getInstance() {
//        if(instance == null) {
//            instance = new SignUpService();
//        }
//        return instance;
//    }
//
//    private SignUpService() {
//        serverFacade = new ServerFacade();
//    }
//
//    public SignUpResponse getSignUpResponse(SignUpRequest request) {
//        SignUpResponse response = serverFacade.getSignedUpUserServerResponse(request);
//        if (response.getUser() != null)
//            SignInService.getInstance().setCurrentUser(response.getUser());
//        return response;
//    }
//}
