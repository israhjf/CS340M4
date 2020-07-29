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
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

import java.util.List;
import java.util.Map;

import edu.byu.model.domain.User;
import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.response.SignUpResponse;

public class UserDAO {

    private static final String tableName = "users";
    private static final String primaryKey = "alias";
    private static final String userFirstNameAttr = "first_name";
    private static final String userLastNameAttr = "last_name";
    private static final String userHashedPasswordAttr = "hashed_password";
    private static final String userImageUrlAttr = "image_url";

    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder
            .standard()
            .withRegion("us-west-2")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);

    /***
     * SignUp Feature
     * @param request
     * @return
     */
    public SignUpResponse getSignedUpUserServerResponse(SignUpRequest request){
        Table table = dynamoDB.getTable(tableName);
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(primaryKey, request.userAlias);

        try {
            System.out.println("Attempting to read the item...");
            Item checkOutcome = table.getItem(spec);

            if (checkOutcome != null){
                return new SignUpResponse("User is already registered!!");
            }

            System.out.println("Adding a new item...");
            PutItemOutcome putOutcome = table.putItem(new Item()
                    .withPrimaryKey(primaryKey, request.getUserAlias())
                    .withString(userFirstNameAttr, request.getFirstName())
                    .withString(userLastNameAttr, request.getLastName())
                    .withString(userHashedPasswordAttr, request.getPassword())
                    .withString(userImageUrlAttr, request.getImageUrl()));

            System.out.println("PutItem succeeded:\n" + putOutcome.getPutItemResult());
            User newUser = new User(request.getFirstName(), request.getLastName(),
                    request.getUserAlias(), request.getImageUrl());
            return new SignUpResponse(newUser);
        }
        catch (Exception e) {
            String message = String.format("Unable to add item: " + request.getUserAlias());
            System.err.println(message);
            System.err.println(e.getMessage());
            return new SignUpResponse(message);
        }
    }

    public void addUserBatch(List<User> users) {

        // Constructor for TableWriteItems takes the name of the table, which I have stored in TABLE_USER
        TableWriteItems items = new TableWriteItems(tableName);

        // Add each user into the TableWriteItems object
        for (User user : users) {
            Item item = new Item()
                    .withPrimaryKey("alias", user.getAlias())
                    .withString("name", user.getName());
            items.addItemToPut(item);

            // 25 is the maximum number of items allowed in a single batch write.
            // Attempting to write more than 25 items will result in an exception being thrown
            if (items.getItemsToPut() != null && items.getItemsToPut().size() == 25) {
                loopBatchWrite(items);
                items = new TableWriteItems(tableName);
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
