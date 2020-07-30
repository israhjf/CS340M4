package edu.byu.server.lambda;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import edu.byu.server.service.PostUpdateFeedMessagesServiceImpl;
import edu.byu.server.service.UpdateFeedsServiceImpl;

public class UpdateFeedsHandler implements RequestHandler<SQSEvent, Void> {
    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        UpdateFeedsServiceImpl service;
        try{
            service = new UpdateFeedsServiceImpl();

            for (SQSEvent.SQSMessage msg : event.getRecords()) {
                System.out.println(new String(msg.getBody()));
                service.updateFeeds(msg);
            }
        }
        catch (Exception e){
            throw new RuntimeException("500");
        }
        return null;
    }
}
