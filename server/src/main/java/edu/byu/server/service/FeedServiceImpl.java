package edu.byu.server.service;

import java.io.IOException;

import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.FeedService;
import edu.byu.model.services.request.FeedRequest;
import edu.byu.model.services.response.FeedResponse;
import edu.byu.server.dao.FeedDAO;

public class FeedServiceImpl implements FeedService {

    @Override
    public FeedResponse getFeedStatuses(FeedRequest request) {
        return getFeedDAO().getFeedStatuses(request);
    }

    FeedDAO getFeedDAO() {
        return new FeedDAO();
    }
}
