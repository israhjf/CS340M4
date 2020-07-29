package edu.byu.model.services;

import java.io.IOException;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.StatusRequest;
import edu.byu.model.services.response.StatusResponse;

public interface StatusService {
    StatusResponse getPostStatusResponse(StatusRequest request)
        throws IOException, TweeterRemoteException;
}
//    /**
//     * The singleton instance.
//     */
//    private static StatusService instance;
//    private final ServerFacade serverFacade;
//
//    public static StatusService getInstance() {
//        if(instance == null) {
//            instance = new StatusService();
//        }
//        return instance;
//    }
//
//    private StatusService() {
//        serverFacade = new ServerFacade();
//    }
//
//    public StatusResponse getPostStatusResponse(StatusRequest request) {
//        StatusResponse response = serverFacade.submitStatusRequest(request);
//        return response;
//    }
//}
