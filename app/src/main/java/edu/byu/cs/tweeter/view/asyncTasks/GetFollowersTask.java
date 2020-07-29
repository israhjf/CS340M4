package edu.byu.cs.tweeter.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import edu.byu.cs.tweeter.presenter.FollowersPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;
import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.FollowersRequest;
import edu.byu.model.services.response.FollowersResponse;

public class GetFollowersTask extends AsyncTask<FollowersRequest, Void, FollowersResponse> {

    private final FollowersPresenter presenter;
    private final GetFollowersObserver observer;

     public interface GetFollowersObserver {
        void followersRetrieved(FollowersResponse followersResponse);
    }

    public GetFollowersTask(FollowersPresenter presenter, GetFollowersObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowersResponse doInBackground(FollowersRequest... followersRequests) {
        FollowersResponse response = null;
        try {
            response = presenter.getFollowers(followersRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        loadImages(response);
        return response;
    }

    private void loadImages(FollowersResponse response) {
        for(User user : response.getFollowers()) {
            Drawable drawable;

            try {
                drawable = ImageUtils.drawableFromUrl(user.getImageUrl());
            } catch (IOException e) {
                Log.e(this.getClass().getName(), e.toString(), e);
                drawable = null;
            }

            ImageCache.getInstance().cacheImage(user, drawable);
        }
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param followersResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(FollowersResponse followersResponse) {

        if(observer != null) {
            observer.followersRetrieved(followersResponse);
        }
    }
}
