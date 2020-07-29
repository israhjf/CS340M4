package edu.byu.model.services.response;

import java.util.List;

import edu.byu.model.domain.Status;

public class FeedResponse extends PagedResponse{
    public List<Status> feedStatuses;

    public FeedResponse(String message) {
        super(false, message, false);
    }

    public FeedResponse(List<Status> feedStatuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.feedStatuses = feedStatuses;
    }

    public List<Status> getFeedStatuses() {
        return feedStatuses;
    }
}
