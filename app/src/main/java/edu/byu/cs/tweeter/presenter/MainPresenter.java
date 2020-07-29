package edu.byu.cs.tweeter.presenter;

import edu.byu.model.services.SignOutService;
import edu.byu.model.services.request.SignOutRequest;
import edu.byu.model.services.response.SignOutResponse;

/**
 * The presenter for the main activity.
 */
public class MainPresenter extends Presenter {

    private final View view;

    /**
     * The interface by which this presenter sends notifications to it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
        void checkSignOutResponse(boolean isSignOutSuccessful);
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public MainPresenter(View view) {
        this.view = view;
    }
}
