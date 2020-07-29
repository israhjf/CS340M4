package edu.byu.server.service;

import java.io.IOException;

import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.SignOutService;
import edu.byu.model.services.request.SignOutRequest;
import edu.byu.model.services.response.SignOutResponse;
//import edu.byu.server.dao.SignInDAO;
import edu.byu.server.dao.SignOutDAO;

public class SignOutServiceImpl implements SignOutService {
    @Override
    public SignOutResponse getSignOutResponse(SignOutRequest request) {
        return getSignOutDAO().getSignedOutUserServerResponse(request);
    }

    SignOutDAO getSignOutDAO() {
        return new SignOutDAO();
    }
}
