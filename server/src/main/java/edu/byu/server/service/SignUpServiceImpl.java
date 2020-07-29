package edu.byu.server.service;

import edu.byu.model.services.SignUpService;
import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.response.SignUpResponse;
import edu.byu.server.dao.UserDAO;

public class SignUpServiceImpl implements SignUpService {
    @Override
    public SignUpResponse getSignUpResponse(SignUpRequest request) {
        return getUserDAO().getSignedUpUserServerResponse(request);
    }

    UserDAO getUserDAO() {
        return new UserDAO();
    }
}
