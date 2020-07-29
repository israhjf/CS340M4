package edu.byu.server.service;

import edu.byu.model.services.FollowService;
import edu.byu.model.services.request.FollowRequest;
import edu.byu.model.services.response.FollowResponse;
import edu.byu.server.dao.FollowsDAO;

public class FollowServiceImpl implements FollowService {

    @Override
    public FollowResponse submitFollowRequest(FollowRequest request){
        return getFollowsDAO().addFollow(request);
    }

    FollowsDAO getFollowsDAO() {
        return new FollowsDAO();
    }
}
