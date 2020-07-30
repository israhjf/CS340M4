package edu.byu.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.model.domain.User;
import edu.byu.model.services.request.FollowRequest;
import edu.byu.model.services.request.FollowersRequest;
import edu.byu.model.services.request.FollowingRequest;
import edu.byu.model.services.request.UnfollowRequest;
import edu.byu.model.services.response.FollowResponse;
import edu.byu.model.services.response.FollowersResponse;
import edu.byu.model.services.response.FollowingResponse;
import edu.byu.model.services.response.UnfollowResponse;

public class FollowsDAO {
    private static final String followsTableName = "follows";
    private static final String followsTablefollowerAttr = "follower_handle";
    private static final String followsTablefolloweeAttr = "followee_handle";

    private static final String primaryKey = "followee_handle";
    private static final String sortKeyIndex = "follower_handle";

    private static final String userFirstNameAttr = "first_name";
    private static final String userLastNameAttr = "last_name";
    private static final String userImageUrlAttr = "image_url";

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);


    public FollowResponse addFollow(FollowRequest request){
        Table table = dynamoDB.getTable(followsTableName);
        // Data to add
        String followerAlias = request.follower.alias;
        String newFolloweeAlias = request.newFollowee.alias;

        try {
            System.out.println("Adding a new item...");
            PutItemOutcome outcome = table
                    .putItem(new Item().withPrimaryKey(
                            followsTablefollowerAttr, followerAlias,
                            followsTablefolloweeAttr, newFolloweeAlias));

            System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());
            return new FollowResponse(true);
        }
        catch (Exception e) {
            System.err.println("Unable to add item: " + followerAlias + " " + newFolloweeAlias);
            System.err.println(e.getMessage());
            return new FollowResponse(false);
        }
    }

    public UnfollowResponse submitUnfollowRequest(UnfollowRequest request){
        UnfollowResponse response = new UnfollowResponse(true);
        return response;
    }


    public FollowersResponse getFollowers(FollowersRequest request) {

        assert request.getLimit() > 0;
        assert request.getFollowee() != null;
        boolean hasMorePages = false;

        //follows
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#followee_handle", primaryKey);

        HashMap<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":followee_handle", new AttributeValue().withS(request.getFollowee().getAlias()));

        QueryRequest query = new QueryRequest()
                .withTableName(followsTableName)
                .withIndexName("follows_index")
                .withKeyConditionExpression("#followee_handle = :followee_handle")
                .withExpressionAttributeNames(nameMap)
                .withExpressionAttributeValues(valueMap)
                .withLimit(request.getLimit());

        if (request.getLastFollower() != null) {
            Map<String, AttributeValue> tmp = new HashMap<>();
            tmp.put(primaryKey, new AttributeValue().withS(request.getFollowee().getAlias()));
            tmp.put(sortKeyIndex, new AttributeValue().withS(request.getLastFollower().getAlias()));
            query = query.withExclusiveStartKey(tmp);
        }

        List<Map<String, AttributeValue>> items = null;
        List<User> followers = new ArrayList<>();
        try {
            QueryResult queryResult = client.query(query);
            items = queryResult.getItems();
            for (Map<String, AttributeValue> item : items){
                String followerAlias = item.get(sortKeyIndex).getS();

                //Users
                Table userTable = dynamoDB.getTable("users");
                GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias",
                        followerAlias);

                System.out.println("Attempting to read the item...");
                Item userItem = userTable.getItem(spec);

                System.out.println("Adding a new item...");
                String alias = userItem.getString("alias");
                String firstName = userItem.getString(userFirstNameAttr);
                String lastName = userItem.getString(userLastNameAttr);
                String imageUrl = userItem.getString(userImageUrlAttr);

                User user = new User(firstName, lastName, alias, imageUrl);
                System.out.println(user.getAlias() + " " + user.getFirstName() + " " +
                        user.getLastName());
                followers.add(user);
            }
            if (queryResult.getLastEvaluatedKey() != null)
                hasMorePages = true;
        }
        catch (Exception e) {
            System.err.println("Unable to query items");
            System.err.println(e.getMessage());
        }

        return new FollowersResponse(followers, hasMorePages);
    }

    public FollowingResponse getFollowees(FollowingRequest request) {

        assert request.getLimit() > 0;
        assert request.getFollower() != null;
        boolean hasMorePages = false;

        //follows
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#follower_handle", "follower_handle");

        HashMap<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":follower_handle", new AttributeValue().withS(request.getFollower().getAlias()));

        QueryRequest query = new QueryRequest()
                .withTableName("follows")
                .withKeyConditionExpression("#follower_handle = :follower_handle")
                .withExpressionAttributeNames(nameMap)
                .withExpressionAttributeValues(valueMap)
                .withLimit(request.getLimit());

        if (request.getLastFollowee() != null) {
            Map<String, AttributeValue> tmp = new HashMap<>();
            tmp.put("follower_handle", new AttributeValue().withS(request.getFollower().getAlias()));
            tmp.put("followee_handle", new AttributeValue().withS(request.getLastFollowee().getAlias()));
            query = query.withExclusiveStartKey(tmp);
        }

        List<Map<String, AttributeValue>> items = null;
        List<User> followees = new ArrayList<>();
        try {
            QueryResult queryResult = client.query(query);
            items = queryResult.getItems();
            for (Map<String, AttributeValue> item : items){
                String followeeAlias = item.get("followee_handle").getS();

                //Users
                Table userTable = dynamoDB.getTable("users");
                GetItemSpec spec = new GetItemSpec().withPrimaryKey("alias",
                        followeeAlias);

                System.out.println("Attempting to read the item...");
                Item userItem = userTable.getItem(spec);

                System.out.println("Adding a new item...");
                String alias = userItem.getString("alias");
                String firstName = userItem.getString(userFirstNameAttr);
                String lastName = userItem.getString(userLastNameAttr);
                String imageUrl = userItem.getString(userImageUrlAttr);

                User user = new User(firstName, lastName, alias, imageUrl);
                followees.add(user);
            }
            if (queryResult.getLastEvaluatedKey() != null)
                hasMorePages = true;
        }
        catch (Exception e) {
            System.err.println("Unable to query items");
            System.err.println(e.getMessage());
        }

        return new FollowingResponse(followees, hasMorePages);
    }

    public List<String> getAllFollowersAliases(String followeeAlias){
        List<String> followersAliases = new ArrayList<>();

        //follows
        HashMap<String, String> nameMap = new HashMap<>();
        nameMap.put("#followee_handle", primaryKey);

        HashMap<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":followee_handle", new AttributeValue().withS(followeeAlias));

        QueryRequest query = new QueryRequest()
                .withTableName(followsTableName)
                .withIndexName("follows_index")
                .withKeyConditionExpression("#followee_handle = :followee_handle")
                .withExpressionAttributeNames(nameMap)
                .withExpressionAttributeValues(valueMap);

        List<Map<String, AttributeValue>> items = null;
        try {
            QueryResult queryResult = client.query(query);
            items = queryResult.getItems();
            for (Map<String, AttributeValue> item : items){
                String followerAlias = item.get(sortKeyIndex).getS();
                followersAliases.add(followerAlias);
            }
        }
        catch (Exception e) {
            System.err.println("Unable to query items");
            System.err.println(e.getMessage());
        }
        return followersAliases;
    }

    public void addFollowersBatch(List<String> followers, String followTarget) {
        // Constructor for TableWriteItems takes the name of the table, which I have stored in TABLE_USER
        TableWriteItems items = new TableWriteItems(followsTableName);

        // Add each user into the TableWriteItems object
        for (String follower : followers) {
            Item item = new Item()
                    .withPrimaryKey(followsTablefollowerAttr, follower)
                    .withString(followsTablefolloweeAttr, followTarget);
            items.addItemToPut(item);

            // 25 is the maximum number of items allowed in a single batch write.
            // Attempting to write more than 25 items will result in an exception being thrown
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems(followsTableName);
            }
        }

        // Write any leftover items
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWrite(items);
        }
    }


    private void loopBatchWrite(TableWriteItems items) {

        // The 'dynamoDB' object is of type DynamoDB and is declared statically in this example
        BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(items);
        System.out.println("Wrote User Batch");

        // Check the outcome for items that didn't make it onto the table
        // If any were not added to the table, try again to write the batch
        while (outcome.getUnprocessedItems().size() > 0) {
            Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();
            outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
            System.out.println("Wrote more Users");
        }
    }
}
