package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.SignInServiceProxy;
import edu.byu.cs.tweeter.model.service.SignUpServiceProxy;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.SignInService;
import edu.byu.model.services.SignUpService;
import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.response.SignUpResponse;

public class SignUpPresenter extends Presenter {

    public final View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response
        // to model updates
        void checkSignUpResponse(boolean isSignUpSuccessful);
    }

    public SignUpPresenter(View view) {
        this.view = view;
    }

    public SignUpResponse signUpUser(SignUpRequest request) throws IOException, TweeterRemoteException {
        SignUpService signUpService = getSignUpService();
        return signUpService.getSignUpResponse(request);
    }

    SignUpService getSignUpService() {
        return new SignUpServiceProxy();
    }
}