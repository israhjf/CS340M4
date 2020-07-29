package edu.byu.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.model.services.request.SignInRequest;
import edu.byu.model.services.response.SignInResponse;
import edu.byu.server.service.SignInServiceImpl;

public class SignInHandler implements RequestHandler<SignInRequest, SignInResponse> {
    @Override
    public SignInResponse handleRequest(SignInRequest request, Context context) {
        if (request.userAlias == null) {
            throw new RuntimeException("400");
        }
        SignInServiceImpl service;
        try{
            service = new SignInServiceImpl();
        }
        catch (Exception e){
            throw new RuntimeException("500");
        }
        return service.getSignInResponse(request);
    }
}
