package edu.byu.model.services;

import java.io.IOException;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.UnfollowRequest;
import edu.byu.model.services.response.UnfollowResponse;

public interface UnfollowService {
    UnfollowResponse submitUnfollowRequest(UnfollowRequest request)
        throws IOException, TweeterRemoteException;
}
//    /**
//     * The singleton instance.
//     */
//    private static UnfollowService instance;
//
//    private final ServerFacade serverFacade;
//
//    /**
//     * Return the singleton instance of this class.
//     *
//     * @return the instance.
//     */
//    public static UnfollowService getInstance() {
//        if(instance == null) {
//            instance = new UnfollowService();
//        }
//
//        return instance;
//    }
//
//    /**
//     * A private constructor created to ensure that this class is a singleton (i.e. that it
//     * cannot be instantiated by external classes).
//     */
//    private UnfollowService() {
//        serverFacade = new ServerFacade();
//    }
//
//    public UnfollowResponse submitUnfollowRequest(UnfollowRequest request) {
//        return serverFacade.submitUnfollowRequest(request);
//    }
//}
