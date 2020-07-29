package edu.byu.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.model.services.request.StatusRequest;
import edu.byu.model.services.response.StatusResponse;
import edu.byu.server.service.SignInServiceImpl;
import edu.byu.server.service.StatusServiceImpl;

public class StatusHandler implements RequestHandler<StatusRequest, StatusResponse> {
    @Override
    public StatusResponse handleRequest(StatusRequest request, Context context) {
        if (request.author == null) {
            throw new RuntimeException("400");
        }
        StatusServiceImpl service;
        try{
            service = new StatusServiceImpl();
        }
        catch (Exception e){
            throw new RuntimeException("500");
        }
        return service.getPostStatusResponse(request);
    }
}
