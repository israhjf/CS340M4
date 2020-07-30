package edu.byu.server.service;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.server.dao.FeedDAO;

public class UpdateFeedsServiceImpl {

    public void updateFeeds(SQSEvent.SQSMessage msg){
        String message_text = msg.getBody();

        final Map<String, SQSEvent.MessageAttribute> messageAttributesStatus;
        messageAttributesStatus = msg.getMessageAttributes();

        String status_alias = messageAttributesStatus.get("status_alias").getStringValue();
        String status_timestamp = messageAttributesStatus.get("timestamp").getStringValue();
        String feed_owner_alias = messageAttributesStatus.get("feed_owner_alias").getStringValue();

        System.out.println("UpdateFeed LAMBDA: " + status_alias + " " + status_timestamp
                + " " + message_text + " " + feed_owner_alias);

        getFeedDAO().updateFollowerFeed(feed_owner_alias, message_text, status_timestamp, status_alias);
    }

    FeedDAO getFeedDAO() { return new FeedDAO(); }
}
