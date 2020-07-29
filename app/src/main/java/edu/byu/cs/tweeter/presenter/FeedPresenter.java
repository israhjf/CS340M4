package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FeedServiceProxy;
import edu.byu.cs.tweeter.model.service.FollowingServiceProxy;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.FeedService;
import edu.byu.model.services.FollowingService;
import edu.byu.model.services.request.FeedRequest;
import edu.byu.model.services.response.FeedResponse;

public class FeedPresenter extends Presenter {
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
    public FeedPresenter(View view) {
        this.view = view;
    }

    public FeedResponse getFeedStatuses(FeedRequest request) throws IOException, TweeterRemoteException {
        FeedService feedService = getFeedService();
        return feedService.getFeedStatuses(request);
    }

    FeedService getFeedService() {
        return new FeedServiceProxy();
    }
}
