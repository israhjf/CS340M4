package edu.byu.cs.tweeter.view.main.followers;

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
import edu.byu.cs.tweeter.presenter.FollowersPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFollowersTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.model.domain.User;
import edu.byu.model.services.request.FollowersRequest;
import edu.byu.model.services.response.FollowersResponse;

public class FollowersFragment extends Fragment implements FollowersPresenter.View{

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;
    private static final int PAGE_SIZE = 10;
    private FollowersPresenter presenter;
    private FollowersRecyclerViewAdapter followersRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        presenter = new FollowersPresenter(this);

        RecyclerView followersRecyclerView = view.findViewById(R.id.followersRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        followersRecyclerView.setLayoutManager(layoutManager);
        followersRecyclerViewAdapter = new FollowersRecyclerViewAdapter();
        followersRecyclerView.setAdapter(followersRecyclerViewAdapter);
        followersRecyclerView.addOnScrollListener(new FollowRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    private class FollowersHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;

        FollowersHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "You selected '" + userName.getText() + "'.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        void bindUser(User user) {
            userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(user));
            userAlias.setText(user.getAlias());
            userName.setText(user.getName());
        }
    }

    private class FollowersRecyclerViewAdapter extends RecyclerView.Adapter<FollowersHolder>
            implements GetFollowersTask.GetFollowersObserver {

        private final List<User> users = new ArrayList<>();
        private User lastFollower;
        private boolean hasMorePages;
        private boolean isLoading = false;

        FollowersRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<User> newUsers) {
            int startInsertPosition = users.size();
            users.addAll(newUsers);
            this.notifyItemRangeInserted(startInsertPosition, newUsers.size());
        }

        void addItem(User user) {
            users.add(user);
            this.notifyItemInserted(users.size() - 1);
        }

        void removeItem(User user) {
            int position = users.indexOf(user);
            users.remove(position);
            this.notifyItemRemoved(position);
        }

        @NonNull
        @Override
        public FollowersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FollowersFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.user_row, parent, false);
            }

            return new FollowersHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FollowersHolder followersHolder, int position) {
            if(!isLoading) {
                followersHolder.bindUser(users.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == users.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFollowersTask getFollowersTask = new GetFollowersTask(presenter, this);
            FollowersRequest request = new FollowersRequest(presenter.getCurrentUser(), PAGE_SIZE, lastFollower);
            getFollowersTask.execute(request);
        }

        @Override
        public void followersRetrieved(FollowersResponse followersResponse) {
            if (followersResponse != null) {
                List<User> followers = followersResponse.getFollowers();

                lastFollower = (followers.size() > 0) ? followers.get(followers.size() - 1) : null;
                hasMorePages = followersResponse.getHasMorePages();

                isLoading = false;
                removeLoadingFooter();
                followersRecyclerViewAdapter.addItems(followers);
            }
        }

        /**
         * Adds a dummy user to the list of users so the RecyclerView will display a view (the
         * loading footer view) at the bottom of the list.
         */
        private void addLoadingFooter() {
            addItem(new User("Dummy", "User", ""));
        }

        /**
         * Removes the dummy user from the list of users so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(users.get(users.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class FollowRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        FollowRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
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

            if (!followersRecyclerViewAdapter.isLoading && followersRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    followersRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
