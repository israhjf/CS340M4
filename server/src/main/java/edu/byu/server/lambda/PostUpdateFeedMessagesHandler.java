package edu.byu.server.lambda;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import edu.byu.server.service.FollowServiceImpl;
import edu.byu.server.service.PostUpdateFeedMessagesServiceImpl;
import edu.byu.server.service.SignInServiceImpl;

public class PostUpdateFeedMessagesHandler implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        PostUpdateFeedMessagesServiceImpl service;
        try{
            service = new PostUpdateFeedMessagesServiceImpl();

            for (SQSEvent.SQSMessage msg : event.getRecords()) {
                System.out.println(new String(msg.getBody()));
                service.postUpdateFeedMessages(msg);
            }
        }
        catch (Exception e){
            throw new RuntimeException("500");
        }
        return null;
    }
}
