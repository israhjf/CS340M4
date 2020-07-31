package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.presenter.SignUpPresenter;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.response.SignUpResponse;

public class SignUpTask extends AsyncTask<SignUpRequest, Void, SignUpResponse> {
    private static final String TAG = "SignUpTask";
    private final SignUpPresenter presenter;

    public SignUpTask(SignUpPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected SignUpResponse doInBackground(SignUpRequest... signUpRequests) {
        SignUpResponse response = null;
        try {
            response = presenter.signUpUser(signUpRequests[0]);
         } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(SignUpResponse signUpResponse) {
        if(signUpResponse == null || signUpResponse.getUser() == null){
            presenter.view.checkSignUpResponse(false);
            Log.e(TAG, "Error: Sign In failed");
        }
        else{
            presenter.view.checkSignUpResponse(true);
            Log.d(TAG, "OnPostExecute");
        }
    }
}
