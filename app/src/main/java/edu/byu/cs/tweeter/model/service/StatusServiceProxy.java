package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;
import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.StatusService;
import edu.byu.model.services.request.StatusRequest;
import edu.byu.model.services.response.SignUpResponse;
import edu.byu.model.services.response.StatusResponse;

public class StatusServiceProxy implements StatusService {

    static final String URL_PATH = "/Status";

    @Override
    public StatusResponse getPostStatusResponse(StatusRequest request) throws IOException, TweeterRemoteException {
        StatusResponse response = getServerFacade().getPostStatusResponse(request, URL_PATH);

//        if(response.isSuccess()) {
//            loadImage(response);
//        }

        return response;
    }

//    private void loadImage(StatusResponse response) throws IOException {
//        User user = response.;
//        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
//        user.setImageBytes(bytes);
//    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
