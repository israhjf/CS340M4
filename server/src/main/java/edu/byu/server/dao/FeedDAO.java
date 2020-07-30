package edu.byu.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.model.domain.Status;
import edu.byu.model.domain.User;
import edu.byu.model.services.request.FeedRequest;
import edu.byu.model.services.request.StatusRequest;
import edu.byu.model.services.response.FeedResponse;
import edu.byu.model.services.response.FollowingResponse;
import edu.byu.model.services.response.StatusResponse;
import edu.byu.model.services.response.StoryResponse;

public class FeedDAO {

    private static final String tableName = "feeds";
    private static final String primaryKey = "owner_alias";
    private static final String userFirstNameAttr = "first_name";
    private static final String userLastNameAttr = "last_name";
    private static final String userImageUrlAttr = "image_url";

    private static final String timestampAttr = "timestamp";
    private static final String messageTextAttr = "message_text";
    private static final String statusAliasAttr = "status_alias";

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);


    public FeedResponse getFeedStatuses(FeedRequest request){

        assert request.getLimit() > 0;
        assert request.getFeedUser() != null;
        boolean hasMorePages = false;

        //Setting up the query
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#feed_owner_alias", "feed_owner_alias");

        HashMap<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":feed_owner_alias", new AttributeValue().withS(request.getFeedUser().getAlias()));

        QueryRequest query = new QueryRequest()
                .withTableName("feeds")
                .withKeyConditionExpression("#feed_owner_alias = :feed_owner_alias")
                .withExpressionAttributeNames(nameMap)
                .withExpressionAttributeValues(valueMap)
                .withLimit(request.getLimit());

        if (request.getLastStatus() != null) {
            Map<String, AttributeValue> tmp = new HashMap<>();
            tmp.put("timestamp", new AttributeValue().withS(request.getLastStatus().getTimestamp()));
            tmp.put("feed_owner_alias", new AttributeValue().withS(request.getLastStatus().getAuthor().getAlias()));
            query = query.withExclusiveStartKey(tmp);
        }

        List<Map<String, AttributeValue>> items = null;
        List<Status> feedStatuses = new ArrayList<>();
        try {
            QueryResult queryResult = client.query(query);
            items = queryResult.getItems();
            for (Map<String, AttributeValue> item : items){
                String statusAlias = item.get("status_alias").getS();
                String statusMessage = item.get("message_text").getS();
                String timestamp = item.get("timestamp").getS();

                //Query user
                Table userTable = dynamoDB.getTable("users");
                GetItemSpec spec = new GetItemSpec()
                        .withPrimaryKey("alias", statusAlias);

                System.out.println("Attempting to read the user...");
                Item userItem = userTable.getItem(spec);

                String alias = userItem.getString("alias");
                String firstName = userItem.getString(userFirstNameAttr);
                String lastName = userItem.getString(userLastNameAttr);
                String imageUrl = userItem.getString(userImageUrlAttr);

                User author = new User(firstName, lastName, alias, imageUrl);
                Status status = new Status(author, statusMessage, timestamp);
                feedStatuses.add(status);
            }
            if (queryResult.getLastEvaluatedKey() != null)
                hasMorePages = true;
        }
        catch (Exception e) {
            System.err.println("Unable to query items");
            System.err.println(e.getMessage());
        }

        return new FeedResponse(feedStatuses, hasMorePages);
    }

    private int getStatusesStartingIndex(Status lastStatus, List<Status> allStatuses) {

        int statusesIndex = 0;

        if(lastStatus != null) {
            // This is a paged request for something after the first page. Find the first item
            // we should return
            for (int i = 0; i < allStatuses.size(); i++) {
                if(lastStatus.equals(allStatuses.get(i))) {
                    // We found the index of the last item returned last time. Increment to get
                    // to the first one we should return
                    statusesIndex = i + 1;
                }
            }
        }
        return statusesIndex;
    }

    /***
     * Post status in feed feature
     */
    public StatusResponse submitStatusRequest(StatusRequest request){

        try {
            System.out.println("Adding a new status...");

            //Uploading to PostStatusQueue
            String messageBody = request.getMessage();
            String postStatusQueueUrl = "https://sqs.us-west-2.amazonaws.com/764200764125/PostStatusQueue";

            //Adding attributes
            final Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
            messageAttributes.put("status_alias", new MessageAttributeValue()
                    .withDataType("String")
                    .withStringValue(request.getAuthor().getAlias()));
            messageAttributes.put("timestamp", new MessageAttributeValue()
                    .withDataType("String")
                    .withStringValue(request.getPostTimeStamp()));

            //Send Message
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(postStatusQueueUrl)
                    .withMessageBody(messageBody)
                    .withMessageAttributes(messageAttributes)
                    .withDelaySeconds(5);

            AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
            SendMessageResult send_msg_result = sqs.sendMessage(send_msg_request);

            String msgId = send_msg_result.getMessageId();
            System.out.println("Message ID: " + msgId);

//            PutItemOutcome item = table.putItem(new Item()
//                    .withPrimaryKey(primaryKey, request.getAuthor().getAlias())
//                    .withString(timestampAttr, request.getPostTimeStamp())
//                    .withString(messageTextAttr, request.getMessage()));

//            System.out.println("PutItem succeeded:\n" + item.getPutItemResult());
            System.out.println("PutItem on PostStatusQueue succeeded");
            return new StatusResponse(true);
        }
        catch (Exception e) {
            String message = String.format("Unable to add item: " + request.getAuthor().getAlias());
            System.err.println(message);
            System.err.println(e.getMessage());
            return new StatusResponse(message);
        }
    }
}
