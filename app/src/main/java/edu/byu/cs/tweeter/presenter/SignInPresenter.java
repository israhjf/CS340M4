package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowingServiceProxy;
import edu.byu.cs.tweeter.model.service.SignInServiceProxy;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.FollowingService;
import edu.byu.model.services.SignInService;
import edu.byu.model.services.request.SignInRequest;
import edu.byu.model.services.response.SignInResponse;

public class SignInPresenter extends Presenter {

    public final View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response
        // to model updates
        void checkSignInResponse(boolean isSignInSuccessful);
    }

    public SignInPresenter(View view) {
        this.view = view;
    }

    public SignInResponse getSignInResponse(SignInRequest request) throws IOException, TweeterRemoteException {
        SignInService signInService = getSignInService();
        return signInService.getSignInResponse(request);
    }

    SignInService getSignInService() {
        return SignInServiceProxy.getInstance();
    }
}