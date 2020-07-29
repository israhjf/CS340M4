package edu.byu.server.service;

import edu.byu.model.services.StoryService;
import edu.byu.model.services.request.StoryRequest;
import edu.byu.model.services.response.StoryResponse;
import edu.byu.server.dao.StoryDAO;

public class StoryServiceImpl implements StoryService {
    @Override
    public StoryResponse getStoryStatuses(StoryRequest request) {
        return getStoryDAO().getStoryStatuses(request);
    }

    StoryDAO getStoryDAO() { return new StoryDAO(); }
}
