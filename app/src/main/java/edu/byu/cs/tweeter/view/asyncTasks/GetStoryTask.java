package edu.byu.cs.tweeter.view.asyncTasks;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.util.ImageUtils;
import edu.byu.model.domain.Status;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.StoryRequest;
import edu.byu.model.services.response.StoryResponse;

public class GetStoryTask extends AsyncTask<StoryRequest, Void, StoryResponse> {
    private final StoryPresenter presenter;
    private final GetStoryObserver observer;

    public interface GetStoryObserver {
        void storyRetrieved(StoryResponse storyResponse);
    }

    public GetStoryTask(StoryPresenter presenter, GetStoryObserver observer) {
        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected StoryResponse doInBackground(StoryRequest... storyRequests) {
        StoryResponse response = null;
        try {
            response = presenter.getStoryStatuses(storyRequests[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TweeterRemoteException e) {
            e.printStackTrace();
        }
        loadImages(response);
        return response;
    }

    private void loadImages(StoryResponse response) {
        for(edu.byu.model.domain.Status status : response.getStoryStatuses()) {
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
     * @param storyResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(StoryResponse storyResponse) {

        if(observer != null) {
            observer.storyRetrieved(storyResponse);
        }
    }
}
