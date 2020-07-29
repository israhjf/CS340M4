package edu.byu.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.model.services.request.FeedRequest;
import edu.byu.model.services.response.FeedResponse;
import edu.byu.server.service.FeedServiceImpl;

public class GetFeedHandler implements RequestHandler<FeedRequest, FeedResponse> {
    @Override
    public FeedResponse handleRequest(FeedRequest request, Context context) {
        if (request.getFeedUser() == null){
            throw new RuntimeException("400");
        }
        FeedServiceImpl service;
        try{
            service = new FeedServiceImpl();
        }
        catch (Exception e){
            throw new RuntimeException("500");
        }
        return service.getFeedStatuses(request);
    }
}
