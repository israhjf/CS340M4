package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.presenter.SignOutPresenter;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.SignOutRequest;
import edu.byu.model.services.response.SignOutResponse;

public class SignOutTask extends AsyncTask<SignOutRequest, Void, SignOutResponse> {
    private static final String TAG = "SignOutTask";
    private final SignOutPresenter presenter;

    public SignOutTask(SignOutPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected SignOutResponse doInBackground(SignOutRequest... signOutRequests) {
        SignOutResponse response = null;
        try {
            response = presenter.signOutUser(signOutRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(SignOutResponse signOutResponse) {
        if(signOutResponse == null || signOutResponse.getUser() == null){
            presenter.getView().checkSignOutResponse(false);
            Log.e(TAG, "Error: Sign In failed");
        }
        else{
            presenter.getView().checkSignOutResponse(true);
            Log.d(TAG, "OnPostExecute");
        }
    }
}
