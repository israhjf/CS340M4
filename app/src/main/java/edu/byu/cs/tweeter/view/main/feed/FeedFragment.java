package edu.byu.cs.tweeter.view.main.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFeedTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.model.domain.Status;
import edu.byu.model.domain.User;
import edu.byu.model.services.request.FeedRequest;
import edu.byu.model.services.response.FeedResponse;

public class FeedFragment extends Fragment implements FeedPresenter.View {
    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;
    private static final int PAGE_SIZE = 10;
    private FeedPresenter presenter;
    private FeedRecyclerViewAdapter feedRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        presenter = new FeedPresenter(this);

        RecyclerView feedRecyclerView = view.findViewById(R.id.feedRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        feedRecyclerView.setLayoutManager(layoutManager);
        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter();
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);
        feedRecyclerView.addOnScrollListener(new FeedRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    private class StatusHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final TextView timeStamp;
        private final TextView message;

        StatusHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.status_userImage);
            userAlias = itemView.findViewById(R.id.status_userAlias);
            userName = itemView.findViewById(R.id.status_userName);
            timeStamp = itemView.findViewById(R.id.status_timestamp);
            message = itemView.findViewById(R.id.status_message);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "You selected '" + userName.getText()
                            + "'.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        void bindStatus(Status status) {
            userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(status.getAuthor()));
            userAlias.setText(status.getAuthor().getAlias());
            userName.setText(status.getAuthor().getName());
            timeStamp.setText(status.getTimestamp());
            message.setText(status.getMessage());
        }
    }

    private class FeedRecyclerViewAdapter extends RecyclerView.Adapter<StatusHolder>
            implements GetFeedTask.GetFeedObserver {

        private final List<Status> feedStatuses = new ArrayList<>();
        private Status lastStatus;
        private boolean hasMorePages;
        private boolean isLoading = false;

        FeedRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<Status> newStatuses) {
            int startInsertPosition = feedStatuses.size();
            feedStatuses.addAll(newStatuses);
            this.notifyItemRangeInserted(startInsertPosition, newStatuses.size());
        }

        void addItem(Status status) {
            feedStatuses.add(status);
            this.notifyItemInserted(feedStatuses.size() - 1);
        }

        void removeItem(Status status) {
            int position = feedStatuses.indexOf(status);
            feedStatuses.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public StatusHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FeedFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);
            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);
            }

            return new StatusHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StatusHolder statusHolder, int position) {
            if(!isLoading) {
                statusHolder.bindStatus(feedStatuses.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return feedStatuses.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == feedStatuses.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFeedTask getFeedTask = new GetFeedTask(presenter, this);
            FeedRequest request = new FeedRequest(presenter.getCurrentUser(), PAGE_SIZE, lastStatus);
            getFeedTask.execute(request);
        }

        @Override
        public void feedRetrieved(FeedResponse feedResponse) {
            if (feedResponse != null) {
                List<Status> feedStatuses = feedResponse.getFeedStatuses();

                lastStatus = (feedStatuses.size() > 0) ? feedStatuses.get(feedStatuses.size() - 1) : null;
                hasMorePages = feedResponse.getHasMorePages();

                isLoading = false;
                removeLoadingFooter();
                feedRecyclerViewAdapter.addItems(feedStatuses);
            }
        }

        /**
         * Adds a dummy user to the list of users so the RecyclerView will display a view (the
         * loading footer view) at the bottom of the list.
         */
        private void addLoadingFooter() {
            addItem(new Status(new User("Dummy", "User", ""),
                    "Status Test", "Today's date"));
        }

        /**
         * Removes the dummy user from the list of users so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(feedStatuses.get(feedStatuses.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class FeedRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        FeedRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        /**
         * Determines whether the user has scrolled to the bottom of the currently available data
         * in the RecyclerView and asks the adapter to load more data if the last load request
         * indicated that there was more data to load.
         *
         * @param recyclerView the RecyclerView.
         * @param dx the amount of horizontal scroll.
         * @param dy the amount of vertical scroll.
         */
        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!feedRecyclerViewAdapter.isLoading && feedRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    feedRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
