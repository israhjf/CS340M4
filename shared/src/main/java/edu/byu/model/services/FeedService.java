package edu.byu.model.services;

import java.io.IOException;

import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.FeedRequest;
import edu.byu.model.services.response.FeedResponse;

public interface FeedService {
    FeedResponse getFeedStatuses(FeedRequest request)
        throws IOException, TweeterRemoteException;
}

//public class FeedService {
//    /**
//     * The singleton instance.
//     */
//    private static FeedService instance;
//
//    private final ServerFacade serverFacade;
//
//    /**
//     * Return the singleton instance of this class.
//     *
//     * @return the instance.
//     */
//    public static FeedService getInstance() {
//        if(instance == null) {
//            instance = new FeedService();
//        }
//
//        return instance;
//    }
//
//    /**
//     * A private constructor created to ensure that this class is a singleton (i.e. that it
//     * cannot be instantiated by external classes).
//     */
//    private FeedService() {
//        serverFacade = new ServerFacade();
//    }
//
//    public FeedResponse getFeedStatuses(FeedRequest request) {
//        return serverFacade.getFeedStatuses(request);
//    }
//}
