package edu.byu.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.model.services.request.StoryRequest;
import edu.byu.model.services.response.StoryResponse;
import edu.byu.server.service.StoryServiceImpl;

public class GetStoryHandler implements RequestHandler<StoryRequest, StoryResponse> {
    @Override
    public StoryResponse handleRequest(StoryRequest request, Context context) {
        if (request.getStoryUser() == null) {
            throw new RuntimeException("400");
        }
        StoryServiceImpl service;
        try{
            service = new StoryServiceImpl();
        }
        catch (Exception e){
            throw new RuntimeException("500");
        }
        return service.getStoryStatuses(request);
    }
}
