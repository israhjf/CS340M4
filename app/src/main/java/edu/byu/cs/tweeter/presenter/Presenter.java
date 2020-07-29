package edu.byu.cs.tweeter.presenter;

import edu.byu.cs.tweeter.model.service.SignInServiceProxy;
import edu.byu.model.domain.User;
import edu.byu.model.services.SignInService;

/**
 * A common base class for all presenters in the application.
 */
public abstract class Presenter {

    /**
     * Returns the currently logged in user.
     *
     * @return the user.
     */
    public User getCurrentUser() {
        return SignInServiceProxy.getInstance().getCurrentUser();
    }
}
