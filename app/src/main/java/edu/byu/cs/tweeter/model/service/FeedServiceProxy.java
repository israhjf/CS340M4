package edu.byu.cs.tweeter.model.service;

import java.io.IOException;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;
import edu.byu.model.domain.Status;
import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.FeedService;
import edu.byu.model.services.request.FeedRequest;
import edu.byu.model.services.response.FeedResponse;

public class FeedServiceProxy implements FeedService {

    static final String URL_PATH = "/GetFeed";

    @Override
    public FeedResponse getFeedStatuses(FeedRequest request) throws IOException, TweeterRemoteException {
        FeedResponse response = getServerFacade().getFeedStatuses(request, URL_PATH);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    private void loadImages(FeedResponse response) throws IOException {
        for(Status status : response.getFeedStatuses()) {
            User user = status.author;
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
