package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.presenter.StatusPresenter;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.StatusRequest;
import edu.byu.model.services.response.StatusResponse;

public class PostStatusTask extends AsyncTask<StatusRequest, Void, StatusResponse> {
    private static final String TAG = "PostStatusTask";
    private final StatusPresenter presenter;

    public PostStatusTask(StatusPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected StatusResponse doInBackground(StatusRequest... statusRequests) {
        StatusResponse response = null;
        try {
            response = presenter.postStatus(statusRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(StatusResponse statusResponse) {
        if(statusResponse == null || statusResponse.isSuccessful() == false){
            presenter.getView().checkPostStatusResponse(false);
            Log.e(TAG, "Error: Sign In failed");
        }
        else{
            presenter.getView().checkPostStatusResponse(true);
            Log.d(TAG, "OnPostExecute");
        }
    }
}
