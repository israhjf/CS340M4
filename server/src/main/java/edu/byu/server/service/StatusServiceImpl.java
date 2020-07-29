package edu.byu.server.service;

import java.io.IOException;

import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.StatusService;
import edu.byu.model.services.request.StatusRequest;
import edu.byu.model.services.response.StatusResponse;
import edu.byu.server.dao.FeedDAO;
//import edu.byu.server.dao.SignUpDAO;
//import edu.byu.server.dao.StatusDAO;
import edu.byu.server.dao.StoryDAO;

public class StatusServiceImpl implements StatusService {
    @Override
    public StatusResponse getPostStatusResponse(StatusRequest request) {
        StatusResponse storyResponse = getStoryDAO().submitStatusRequest(request);
        StatusResponse feedResponse = getFeedDAO().submitStatusRequest(request);

        if (!storyResponse.isSuccessful && !feedResponse.isSuccessful)
            return new StatusResponse("Both STORY and FEED status update failed");
        else if (!storyResponse.isSuccessful)
            return storyResponse;
        else if (!feedResponse.isSuccessful)
            return feedResponse;

        return new StatusResponse(true);
    }

    StoryDAO getStoryDAO() { return new StoryDAO(); }

    FeedDAO getFeedDAO() { return new FeedDAO(); }
}
