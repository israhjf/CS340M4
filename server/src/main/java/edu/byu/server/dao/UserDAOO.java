package edu.byu.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;

import edu.byu.model.domain.User;
import edu.byu.model.services.request.SignInRequest;
import edu.byu.model.services.response.SignInResponse;

public class UserDAOO {
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
     * SignIn Feature
     * @param request
     * @return
     */
    public SignInResponse getSignedInUserServerResponse(SignInRequest request){
        Table table = dynamoDB.getTable(tableName);
        GetItemSpec spec = new GetItemSpec().withPrimaryKey(primaryKey, request.userAlias);

        try {
            System.out.println("Attempting to read the item...");
            Item item = table.getItem(spec);

            if (item == null){
                return new SignInResponse("User not found!!");
            }

            System.out.println("Adding a new item...");
            String alias = item.getString(primaryKey);
            String firstName = item.getString(userFirstNameAttr);
            String lastName = item.getString(userLastNameAttr);
            String password = item.getString(userHashedPasswordAttr);
            String imageUrl = item.getString(userImageUrlAttr);

            if (!request.password.equals(password)){
                return new SignInResponse("Invalid password!");
            }

            System.out.println("SignIn succeeded:\n" + alias);
            User user = new User(firstName, lastName, alias, imageUrl);
            return new SignInResponse(user);
        }
        catch (Exception e) {
            String message = String.format("Error while singing in: " + request.getUserAlias());
            System.err.println(message);
            System.err.println(e.getMessage());
            return new SignInResponse(message);
        }
    }
}
