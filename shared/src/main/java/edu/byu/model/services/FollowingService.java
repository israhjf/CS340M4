package edu.byu.model.services;

import java.io.IOException;

import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.FollowingRequest;
import edu.byu.model.services.response.FollowingResponse;

public interface FollowingService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    FollowingResponse getFollowees(FollowingRequest request)
            throws IOException, TweeterRemoteException;
}

///**
// * Contains the business logic for getting the users a user is following.
// */
//public class FollowingService {
//
//    /**
//     * The singleton instance.
//     */
//    private static FollowingService instance;
//
//    private final ServerFacade serverFacade;
//
//    /**
//     * Return the singleton instance of this class.
//     *
//     * @return the instance.
//     */
//    public static FollowingService getInstance() {
//        if(instance == null) {
//            instance = new FollowingService();
//        }
//
//        return instance;
//    }
//
//    /**
//     * A private constructor created to ensure that this class is a singleton (i.e. that it
//     * cannot be instantiated by external classes).
//     */
//    private FollowingService() {
//        serverFacade = new ServerFacade();
//    }
//
//    /**
//     * Returns the users that the user specified in the request is following. Uses information in
//     * the request object to limit the number of followees returned and to return the next set of
//     * followees after any that were returned in a previous request. Uses the {@link ServerFacade} to
//     * get the followees from the server.
//     *
//     * @param request contains the data required to fulfill the request.
//     * @return the followees.
//     */
//    public FollowingResponse getFollowees(FollowingRequest request) {
//        return serverFacade.getFollowees(request);
//    }
//}
