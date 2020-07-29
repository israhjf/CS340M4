package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;
import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.SignOutService;
import edu.byu.model.services.request.SignOutRequest;
import edu.byu.model.services.response.SignInResponse;
import edu.byu.model.services.response.SignOutResponse;

public class SignOutServiceProxy implements SignOutService {

    static final String URL_PATH = "/SignOut";

    @Override
    public SignOutResponse getSignOutResponse(SignOutRequest request) throws IOException, TweeterRemoteException {
        SignOutResponse response = getServerFacade().getSignOutResponse(request, URL_PATH);

        if(response.isSuccess()) {
            loadImage(response);
        }

        return response;
    }

    private void loadImage(SignOutResponse response) throws IOException {
        User user = response.getUser();
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
