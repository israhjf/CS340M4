package edu.byu.server.service;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.byu.server.dao.FollowsDAO;

public class PostUpdateFeedMessagesServiceImpl {

    public void postUpdateFeedMessages(SQSEvent.SQSMessage msg){
        String message_text = msg.getBody();

        final Map<String, SQSEvent.MessageAttribute> messageAttributesStatus;
        messageAttributesStatus = msg.getMessageAttributes();

        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        String status_alias = messageAttributesStatus.get("status_alias").getStringValue();
        String status_timestamp = messageAttributesStatus.get("timestamp").getStringValue();

        System.out.println("PostUpdate LAMBDA: " + status_alias + " " + status_timestamp
                + " " + message_text);

        //Query followers
        List<String> followersAliases = getFollowsDAO().getAllFollowersAliases(status_alias);
        String postStatusQueueUrl = "https://sqs.us-west-2.amazonaws.com/764200764125/UpdateFeedQueue";

        List<SendMessageBatchRequestEntry> entriesList = new ArrayList<SendMessageBatchRequestEntry>();
        //Create UpdatefeedRequest and submit to UpdateFeedQueue
        for (String followerAlias : followersAliases) {
            System.out.println("PostUpdate LAMBDA follower alias: " + followerAlias);
            try {
                System.out.println("Adding a new status...");

                //Adding attributes
                final Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
                messageAttributes.put("status_alias", new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(status_alias));
                messageAttributes.put("timestamp", new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(status_timestamp));
                messageAttributes.put("feed_owner_alias", new MessageAttributeValue()
                        .withDataType("String")
                        .withStringValue(followerAlias));

                //Send Message
                SendMessageBatchRequestEntry send_msg_request_entry = new SendMessageBatchRequestEntry()
                        .withMessageBody(message_text)
                        .withMessageAttributes(messageAttributes);

                entriesList.add(send_msg_request_entry);

                if (entriesList.size() >= 25){
                    SendMessageBatchRequest send_batch_request = new SendMessageBatchRequest()
                            .withQueueUrl(postStatusQueueUrl)
                            .withEntries(entriesList);
                    sqs.sendMessageBatch(send_batch_request);
                    entriesList.clear();
                }

                //String msgId = send_msg_result.getMessageId();
                //System.out.println("Message ID: " + msgId);
                System.out.println("PutItem on UpdateFeedQueue succeeded");
            } catch (Exception e) {
                String message = String.format("Unable to add item for follower: " + followerAlias);
                System.err.println(message);
                System.err.println(e.getMessage());
            }
        }
        //Last batch of entries
        SendMessageBatchRequest send_batch_request = new SendMessageBatchRequest()
                .withQueueUrl(postStatusQueueUrl)
                .withEntries(entriesList);
        sqs.sendMessageBatch(send_batch_request);
        entriesList.clear();
    }

    FollowsDAO getFollowsDAO() {
        return new FollowsDAO();
    }
}
