package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.SignOutServiceProxy;
import edu.byu.cs.tweeter.model.service.SignUpServiceProxy;
import edu.byu.cs.tweeter.view.main.MainActivity;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.SignOutService;
import edu.byu.model.services.SignUpService;
import edu.byu.model.services.request.SignOutRequest;
import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.response.SignOutResponse;
import edu.byu.model.services.response.SignUpResponse;

public class SignOutPresenter extends Presenter{
    public final MainActivity view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response
        // to model updates
        void checkSignOutResponse(boolean isSignOutSuccessful);
    }

    public MainActivity getView() {
        return view;
    }

    public SignOutPresenter(MainActivity view) {
        this.view = view;
    }

    public SignOutResponse signOutUser(SignOutRequest request) throws IOException, TweeterRemoteException {
        SignOutService signOutService = getSignOutService();
        return signOutService.getSignOutResponse(request);
    }

    SignOutService getSignOutService() {
        return new SignOutServiceProxy();
    }
}
