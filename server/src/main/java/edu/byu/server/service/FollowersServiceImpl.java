package edu.byu.server.service;

import edu.byu.model.services.FollowersService;
import edu.byu.model.services.request.FollowersRequest;
import edu.byu.model.services.response.FollowersResponse;
import edu.byu.server.dao.FollowsDAO;

public class FollowersServiceImpl implements FollowersService {
    @Override
    public FollowersResponse getFollowers(FollowersRequest request) {
        return getFollowsDAO().getFollowers(request);
    }

    FollowsDAO getFollowsDAO() {
        return new FollowsDAO();
    }
}
