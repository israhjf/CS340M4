package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;
import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.SignUpService;
import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.response.SignOutResponse;
import edu.byu.model.services.response.SignUpResponse;

public class SignUpServiceProxy implements SignUpService {

    static final String URL_PATH = "/SignUp";

    @Override
    public SignUpResponse getSignUpResponse(SignUpRequest request) throws IOException, TweeterRemoteException {
        SignUpResponse response = getServerFacade().getSignUpResponse(request, URL_PATH);

        if(response.isSuccess()) {
            loadImage(response);
            SignInServiceProxy.getInstance().setCurrentUser(response.user);
        }

        return response;
    }

    private void loadImage(SignUpResponse response) throws IOException {
        User user = response.getUser();
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
