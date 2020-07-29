package edu.byu.model.services;

import java.io.IOException;

import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.request.LoginRequest;
import edu.byu.model.services.response.LoginResponse;

public interface LoginService {

    LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException;
}


///**
// * Contains the business logic for login and sign up.
// */
//public class LoginService {
//
//    /**
//     * The singleton instance.
//     */
//    private static LoginService instance;
//
//    /**
//     * The logged in user.
//     */
//    private User currentUser;
//
//    /**
//     * Return the singleton instance of this class.
//     *
//     * @return the instance.
//     */
//    public static LoginService getInstance() {
//        if(instance == null) {
//            instance = new LoginService();
//        }
//
//        return instance;
//    }
//
//    /**
//     * A private constructor created to ensure that this class is a singleton (i.e. that it
//     * cannot be instantiated by external classes).
//     */
//    private LoginService() {
//        // TODO: Remove when the actual login functionality exists.
//        currentUser = new User("Test", "User",
//                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//        setCurrentUser(currentUser);
//    }
//
//    /**
//     * Returns the currently logged in user.
//     *
//     * @return the user.
//     */
//    public User getCurrentUser() {
//        return currentUser;
//    }
//
//    private void setCurrentUser(User currentUser) {
//        this.currentUser = currentUser;
//    }
//}
