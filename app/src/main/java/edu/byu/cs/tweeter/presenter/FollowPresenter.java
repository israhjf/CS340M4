package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FeedServiceProxy;
import edu.byu.cs.tweeter.model.service.FollowServiceProxy;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.FeedService;
import edu.byu.model.services.FollowService;
import edu.byu.model.services.request.FollowRequest;
import edu.byu.model.services.response.FollowResponse;

public class FollowPresenter extends Presenter{
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
    public FollowPresenter(View view) {
        this.view = view;
    }

    public FollowResponse getFollowers(FollowRequest request) throws IOException, TweeterRemoteException {
        FollowService followService = getFollowService();
        return followService.submitFollowRequest(request);
    }

    FollowService getFollowService() {
        return new FollowServiceProxy();
    }
}
