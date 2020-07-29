package edu.byu.cs.tweeter.view.asyncTasks;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.presenter.SignInPresenter;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.SignInRequest;
import edu.byu.model.services.response.SignInResponse;


public class SignInTask extends AsyncTask<SignInRequest, Void, SignInResponse> {
    private static final String TAG = "SignInTask";
    private final SignInPresenter presenter;

    public SignInTask(SignInPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected SignInResponse doInBackground(SignInRequest... signInRequests) {
        SignInResponse response = null;
        try {
            response = presenter.getSignInResponse(signInRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(SignInResponse signInResponse) {
        if(signInResponse == null || signInResponse.getUser() == null){
            presenter.view.checkSignInResponse(false);
            Log.e(TAG, "Error: Sign In failed");
        }
        else{
            presenter.view.checkSignInResponse(true);
            Log.d(TAG, "OnPostExecute");
        }
    }
}