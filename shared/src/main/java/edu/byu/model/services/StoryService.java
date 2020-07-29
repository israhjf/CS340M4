package edu.byu.model.services;

import java.io.IOException;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.StoryRequest;
import edu.byu.model.services.response.StoryResponse;

public interface StoryService {
    StoryResponse getStoryStatuses(StoryRequest request)
        throws IOException, TweeterRemoteException;
}
//    /**
//     * The singleton instance.
//     */
//    private static StoryService instance;
//
//    private final ServerFacade serverFacade;
//
//    public static StoryService getInstance() {
//        if(instance == null) {
//            instance = new StoryService();
//        }
//
//        return instance;
//    }
//
//    private StoryService() {
//        serverFacade = new ServerFacade();
//    }
//
//    public StoryResponse getStoryStatuses(StoryRequest request) {
//        return serverFacade.getStoryStatuses(request);
//    }
//}
