package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.SignUpServiceProxy;
import edu.byu.cs.tweeter.model.service.StoryServiceProxy;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.SignUpService;
import edu.byu.model.services.StoryService;
import edu.byu.model.services.request.StoryRequest;
import edu.byu.model.services.response.StoryResponse;

public class StoryPresenter extends Presenter {
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
    public StoryPresenter(View view) {
        this.view = view;
    }

    public StoryResponse getStoryStatuses(StoryRequest request) throws IOException, TweeterRemoteException {
        StoryService storyService = getStoryService();
        return storyService.getStoryStatuses(request);
    }

    StoryService getStoryService() {
        return new StoryServiceProxy();
    }
}
