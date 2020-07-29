package edu.byu.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.response.SignUpResponse;
import edu.byu.server.service.SignInServiceImpl;
import edu.byu.server.service.SignUpServiceImpl;

public class SignUpHandler implements RequestHandler<SignUpRequest, SignUpResponse> {
    @Override
    public SignUpResponse handleRequest(SignUpRequest request, Context context) {
        if (request.userAlias == null) {
            throw new RuntimeException("400");
        }
        SignUpServiceImpl service;
        try{
            service = new SignUpServiceImpl();
        }
        catch (Exception e){
            throw new RuntimeException("500");
        }
        return service.getSignUpResponse(request);
    }
}
