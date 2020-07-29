package edu.byu.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.model.services.request.FollowRequest;
import edu.byu.model.services.response.FollowResponse;
import edu.byu.server.service.FollowServiceImpl;

public class FollowHandler implements RequestHandler<FollowRequest, FollowResponse> {
    @Override
    public FollowResponse handleRequest(FollowRequest request, Context context) {
        if (request.getFollower() == null || request.getNewFollowee() == null){
            throw new RuntimeException("400");
        }
        FollowServiceImpl service;
        try{
            service = new FollowServiceImpl();
        }
        catch (Exception e){
            throw new RuntimeException("500");
        }
        return service.submitFollowRequest(request);
    }
}
