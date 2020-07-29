package edu.byu.model.services.response;

import java.util.List;

import edu.byu.model.domain.Status;

public class StoryResponse extends PagedResponse{

    public List<Status> storyStatuses;

    public StoryResponse(String message) {
        super(false, message, false);
    }

    public StoryResponse(List<Status> storyStatuses, boolean hasMorePages) {
        super(true, hasMorePages);
        this.storyStatuses = storyStatuses;
    }

    public List<Status> getStoryStatuses() {
        return storyStatuses;
    }
}
