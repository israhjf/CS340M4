package edu.byu.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.model.services.request.SignOutRequest;
import edu.byu.model.services.response.SignOutResponse;
import edu.byu.server.service.SignInServiceImpl;
import edu.byu.server.service.SignOutServiceImpl;

public class SignOutHandler implements RequestHandler<SignOutRequest, SignOutResponse> {
    @Override
    public SignOutResponse handleRequest(SignOutRequest request, Context context) {
        if (request.getUser() == null) {
            throw new RuntimeException("400");
        }
        SignOutServiceImpl service;
        try{
            service = new SignOutServiceImpl();
        }
        catch (Exception e){
            throw new RuntimeException("500");
        }
        return service.getSignOutResponse(request);
    }
}
