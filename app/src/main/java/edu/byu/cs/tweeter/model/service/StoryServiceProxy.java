package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;
import edu.byu.model.domain.Status;
import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.StoryService;
import edu.byu.model.services.request.StoryRequest;
import edu.byu.model.services.response.FeedResponse;
import edu.byu.model.services.response.StoryResponse;

public class StoryServiceProxy implements StoryService {

    static final String URL_PATH = "/GetStory";

    @Override
    public StoryResponse getStoryStatuses(StoryRequest request) throws IOException, TweeterRemoteException {
        StoryResponse response = getServerFacade().getStoryStatuses(request, URL_PATH);

        if(response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }

    private void loadImages(StoryResponse response) throws IOException {
        for(Status status : response.getStoryStatuses()) {
            User user = status.author;
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
