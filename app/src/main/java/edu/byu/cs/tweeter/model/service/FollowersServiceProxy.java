package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;
import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.FollowersService;
import edu.byu.model.services.request.FollowersRequest;
import edu.byu.model.services.response.FollowersResponse;
import edu.byu.model.services.response.FollowingResponse;

public class FollowersServiceProxy implements FollowersService {
    static final String URL_PATH = "/GetFollowers";

    @Override
    public FollowersResponse getFollowers(FollowersRequest request) throws IOException, TweeterRemoteException {
        FollowersResponse response = getServerFacade().getFollowers(request, URL_PATH);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    private void loadImages(FollowersResponse response) throws IOException {
        for(User user : response.getFollowers()) {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
