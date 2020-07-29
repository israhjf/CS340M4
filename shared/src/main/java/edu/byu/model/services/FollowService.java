package edu.byu.model.services;

import java.io.IOException;

import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.FollowRequest;
import edu.byu.model.services.response.FollowResponse;

public interface FollowService {
    FollowResponse submitFollowRequest(FollowRequest request)
        throws IOException,TweeterRemoteException;
}

//    /**
//     * The singleton instance.
//     */
//    private static FollowService instance;
//
//    private final ServerFacade serverFacade;
//
//    /**
//     * Return the singleton instance of this class.
//     *
//     * @return the instance.
//     */
//    public static FollowService getInstance() {
//        if(instance == null) {
//            instance = new FollowService();
//        }
//
//        return instance;
//    }
//
//    /**
//     * A private constructor created to ensure that this class is a singleton (i.e. that it
//     * cannot be instantiated by external classes).
//     */
//    private FollowService() {
//        serverFacade = new ServerFacade();
//    }
//
//    public FollowResponse submitFollowRequest(FollowRequest request) {
//        return serverFacade.submitFollowRequest(request);
//    }
//}
