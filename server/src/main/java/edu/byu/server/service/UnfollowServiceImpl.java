package edu.byu.server.service;

import edu.byu.model.services.UnfollowService;
import edu.byu.model.services.request.UnfollowRequest;
import edu.byu.model.services.response.UnfollowResponse;
import edu.byu.server.dao.FollowsDAO;
//import edu.byu.server.dao.UnfollowDAO;

public class UnfollowServiceImpl implements UnfollowService {
    @Override
    public UnfollowResponse submitUnfollowRequest(UnfollowRequest request) {
        return getFollowsDAO().submitUnfollowRequest(request);
    }

    FollowsDAO getFollowsDAO() {
        return new FollowsDAO();
    }
}
