package edu.byu.server.service;

import edu.byu.model.services.FollowingService;
import edu.byu.model.services.request.FollowingRequest;
import edu.byu.model.services.response.FollowingResponse;
//import edu.byu.server.dao.FollowingDAO;
import edu.byu.server.dao.FollowsDAO;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceImpl implements FollowingService {

    @Override
    public FollowingResponse getFollowees(FollowingRequest request) {
        return getFollowsDAO().getFollowees(request);
    }

    FollowsDAO getFollowsDAO() {
        return new FollowsDAO();
    }
}

