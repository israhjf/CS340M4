package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.SignUpServiceProxy;
import edu.byu.cs.tweeter.model.service.UnfollowServiceProxy;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.SignUpService;
import edu.byu.model.services.UnfollowService;
import edu.byu.model.services.request.UnfollowRequest;
import edu.byu.model.services.response.UnfollowResponse;

public class UnfollowPresenter extends Presenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public UnfollowPresenter(View view) {
        this.view = view;
    }

    public UnfollowResponse getFollowers(UnfollowRequest request) throws IOException, TweeterRemoteException {
        UnfollowService unfollowService = getUnfollowService();
        return unfollowService.submitUnfollowRequest(request);
    }

    UnfollowService getUnfollowService() {
        return new UnfollowServiceProxy();
    }
}
