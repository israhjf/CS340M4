package edu.byu.server.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.model.services.request.FollowingRequest;
import edu.byu.model.services.response.FollowingResponse;
import edu.byu.server.service.FollowingServiceImpl;

/**
 * An AWS lambda function that returns the users a user is following.
 */
public class GetFollowingHandler implements RequestHandler<FollowingRequest, FollowingResponse> {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the followees.
     */
    @Override
    public FollowingResponse handleRequest(FollowingRequest request, Context context) {
        System.out.print(request.follower.toString());
        if (request.getFollower() == null) {
            throw new RuntimeException("400");
        }
        FollowingServiceImpl service;
        try{
            service = new FollowingServiceImpl();
        }
        catch (Exception e){
            throw new RuntimeException("500");
        }
        return service.getFollowees(request);
    }
}