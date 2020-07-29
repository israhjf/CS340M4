package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;
import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.FollowService;
import edu.byu.model.services.request.FollowRequest;
import edu.byu.model.services.response.FollowResponse;
import edu.byu.model.services.response.SignInResponse;

public class FollowServiceProxy implements FollowService {

    static final String URL_PATH = "/Follow";

    @Override
    public FollowResponse submitFollowRequest(FollowRequest request) throws IOException, TweeterRemoteException {
        FollowResponse response = getServerFacade().submitFollowRequest(request, URL_PATH);
//        if(response.isSuccess()) {
//            loadImage(response);
//        }
        return response;
    }

//    private void loadImage(FollowResponse response) throws IOException {
//        User user = response.ge;
//        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
//        user.setImageBytes(bytes);
//    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
