package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;
import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.SignInService;
import edu.byu.model.services.request.SignInRequest;
import edu.byu.model.services.response.SignInResponse;

public class SignInServiceProxy implements SignInService {

    static final String URL_PATH = "/SignIn";

    /**
     * The singleton instance.
     */

    private static SignInServiceProxy instance = new SignInServiceProxy();
    private User currentUser;

    private SignInServiceProxy() {}

    public static SignInServiceProxy getInstance() {
        return instance;
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public SignInResponse getSignInResponse(SignInRequest request) throws IOException, TweeterRemoteException {
        SignInResponse response = getServerFacade().getSignInResponse(request, URL_PATH);

        if(response.isSuccess()) {
            loadImage(response);
            setCurrentUser(response.getUser());
        }

        return response;
    }

    private void loadImage(SignInResponse response) throws IOException {
        User user = response.getUser();
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
