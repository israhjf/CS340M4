package edu.byu.server.service;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.byu.model.domain.User;
import edu.byu.server.dao.FollowsDAO;

public class PostUpdateFeedMessagesServiceImpl {

    public void postUpdateFeedMessages(SQSEvent.SQSMessage msg){
        String messageText = msg.getBody();

        final Map<String, String> messageAttributes;
        messageAttributes = msg.getAttributes();



        //List<User> followers = getFollowsDAO().getFollowers();
    }

    FollowsDAO getFollowsDAO() {
        return new FollowsDAO();
    }
}
