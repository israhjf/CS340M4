package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.UnfollowService;
import edu.byu.model.services.request.UnfollowRequest;
import edu.byu.model.services.response.FollowResponse;
import edu.byu.model.services.response.UnfollowResponse;

public class UnfollowServiceProxy implements UnfollowService {

    static final String URL_PATH = "/Unfollow";

    @Override
    public UnfollowResponse submitUnfollowRequest(UnfollowRequest request) throws IOException, TweeterRemoteException {
        UnfollowResponse response = getServerFacade().submitUnfollowRequest(request, URL_PATH);
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
