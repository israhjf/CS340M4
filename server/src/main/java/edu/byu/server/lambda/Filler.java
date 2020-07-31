package edu.byu.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.ArrayList;
import java.util.List;

import edu.byu.model.domain.User;
//import edu.byu.server.dao.FollowDAO;
import edu.byu.server.dao.FollowsDAO;
import edu.byu.server.dao.UserDAO;

public class Filler implements RequestHandler<Void, Void> {
    // How many follower users to add
    // We recommend you test this with a smaller number first, to make sure it works for you
    private final static int NUM_USERS = 10000;

    // The alias of the user to be followed by each user created
    // This example code does not add the target user, that user must be added separately.
    private final static String FOLLOW_TARGET = "@israel";

    @Override
    public Void handleRequest(Void input, Context context) {
        // Get instance of DAOs by way of the Abstract Factory Pattern
        UserDAO userDAO = new UserDAO();
        FollowsDAO followsDAO = new FollowsDAO();

        List<String> followers = new ArrayList<>();
        List<User> users = new ArrayList<>();

        // Iterate over the number of users you will create
        for (int i = 1; i <= NUM_USERS; i++) {

            String name = "Guy " + i;
            String alias = "guy" + i;

            // Note that in this example, a UserDTO only has a name and an alias.
            // The url for the profile image can be derived from the alias in this example
            User user = new User(name, name, alias, "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
            users.add(user);

            // Note that in this example, to represent a follows relationship, only the aliases
            // of the two users are needed
            followers.add(alias);
        }

        // Call the DAOs for the database logic
        if (users.size() > 0) {
            userDAO.addUserBatch(users);
        }
        if (followers.size() > 0) {
            followsDAO.addFollowersBatch(followers, FOLLOW_TARGET);
        }
        return null;
    }
}