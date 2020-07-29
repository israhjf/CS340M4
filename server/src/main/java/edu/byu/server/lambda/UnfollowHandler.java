package edu.byu.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.model.services.request.UnfollowRequest;
import edu.byu.model.services.response.UnfollowResponse;
import edu.byu.server.service.SignInServiceImpl;
import edu.byu.server.service.UnfollowServiceImpl;

public class UnfollowHandler implements RequestHandler<UnfollowRequest, UnfollowResponse> {
    @Override
    public UnfollowResponse handleRequest(UnfollowRequest request, Context context) {
        if (request.getOldFollowee() == null || request.getOldFollowing() == null) {
            throw new RuntimeException("400");
        }
        UnfollowServiceImpl service;
        try{
            service = new UnfollowServiceImpl();
        }
        catch (Exception e){
            throw new RuntimeException("500");
        }
        return service.submitUnfollowRequest(request);
    }
}
