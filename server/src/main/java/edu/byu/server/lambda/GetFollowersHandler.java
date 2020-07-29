package edu.byu.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.model.services.request.FollowersRequest;
import edu.byu.model.services.response.FollowersResponse;
import edu.byu.server.service.FollowersServiceImpl;

public class GetFollowersHandler implements RequestHandler<FollowersRequest, FollowersResponse> {
    @Override
    public FollowersResponse handleRequest(FollowersRequest request, Context context) {
        if (request.getFollowee() == null){
            throw new RuntimeException("400");
        }
        FollowersServiceImpl service;
        try{
            service = new FollowersServiceImpl();
        }
        catch (Exception e){
            throw new RuntimeException("500");
        }
        return service.getFollowers(request);
    }
}