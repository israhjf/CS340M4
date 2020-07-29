package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.SignUpServiceProxy;
import edu.byu.cs.tweeter.model.service.StatusServiceProxy;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.SignUpService;
import edu.byu.model.services.StatusService;
import edu.byu.model.services.request.StatusRequest;
import edu.byu.model.services.response.StatusResponse;

public class StatusPresenter extends Presenter {

    public final View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response
        // to model updates
        void checkPostStatusResponse(boolean isSuccesful);
    }

    public View getView() {
        return view;
    }

    public StatusPresenter(View view) {
        this.view = view;
    }

    public StatusResponse postStatus(StatusRequest request) throws IOException, TweeterRemoteException {
        StatusService statusService = getStatusService();
        return statusService.getPostStatusResponse(request);
    }

    StatusService getStatusService() {
        return new StatusServiceProxy();
    }
}