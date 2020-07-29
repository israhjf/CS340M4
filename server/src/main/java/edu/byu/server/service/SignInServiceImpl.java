package edu.byu.server.service;

import edu.byu.model.services.SignInService;
import edu.byu.model.services.request.SignInRequest;
import edu.byu.model.services.response.SignInResponse;
//import edu.byu.server.dao.FollowingDAO;
import edu.byu.server.dao.UserDAO;
//import edu.byu.server.dao.UserDAOO;

public class SignInServiceImpl implements SignInService {

    @Override
    public SignInResponse getSignInResponse(SignInRequest request) {
        return getUserDAO().getSignedInUserServerResponse(request);
    }

    UserDAO getUserDAO() {
        return new UserDAO();
    }
}
