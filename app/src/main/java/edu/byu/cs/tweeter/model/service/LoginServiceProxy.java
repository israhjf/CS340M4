package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.util.ByteArrayUtils;
import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.LoginService;
import edu.byu.model.services.request.LoginRequest;
import edu.byu.model.services.response.LoginResponse;

/**
 * Contains the business logic to support the login operation.
 */
public class LoginServiceProxy implements LoginService {

    private static final String URL_PATH = "/login";

    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException {
        ServerFacade serverFacade = getServerFacade();
        LoginResponse loginResponse = serverFacade.login(request, URL_PATH);

        if(loginResponse.isSuccess()) {
            loadImage(loginResponse.getUser());
        }

        return loginResponse;
    }

    /**
     * Loads the profile image data for the user.
     *
     * @param user the user whose profile image data is to be loaded.
     */
    private void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
