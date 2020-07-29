package edu.byu.cs.tweeter.view.asyncTasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;


import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;
import edu.byu.model.domain.Status;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.FeedRequest;
import edu.byu.model.services.response.FeedResponse;

public class GetFeedTask extends AsyncTask<FeedRequest, Void, FeedResponse> {
    private final FeedPresenter presenter;
    private final GetFeedObserver observer;

    public interface GetFeedObserver {
        void feedRetrieved(FeedResponse feedResponse);
    }

    public GetFeedTask(FeedPresenter presenter, GetFeedObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FeedResponse doInBackground(FeedRequest... feedRequests) {
        FeedResponse response = null;
        try {
            response = presenter.getFeedStatuses(feedRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        loadImages(response);
        return response;
    }

    private void loadImages(FeedResponse response) {
        for(edu.byu.model.domain.Status status : response.getFeedStatuses()) {
            Drawable drawable;

            try {
                drawable = ImageUtils.drawableFromUrl(status.getAuthor().getImageUrl());
            } catch (IOException e) {
                Log.e(this.getClass().getName(), e.toString(), e);
                drawable = null;
            }

            ImageCache.getInstance().cacheImage(status.getAuthor(), drawable);
        }
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param feedResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(FeedResponse feedResponse) {
        if(observer != null) {
            observer.feedRetrieved(feedResponse);
        }
    }
}
