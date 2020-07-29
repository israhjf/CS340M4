package edu.byu.cs.tweeter.model.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byu.cs.tweeter.BuildConfig;
import edu.byu.model.domain.Follow;
import edu.byu.model.domain.Status;
import edu.byu.model.domain.User;
import edu.byu.model.net.TweeterRemoteException;
import edu.byu.model.services.SignInService;
import edu.byu.model.services.request.FeedRequest;
import edu.byu.model.services.request.FollowRequest;
import edu.byu.model.services.request.FollowersRequest;
import edu.byu.model.services.request.FollowingRequest;
import edu.byu.model.services.request.LoginRequest;
import edu.byu.model.services.request.StatusRequest;
import edu.byu.model.services.request.SignInRequest;
import edu.byu.model.services.request.SignOutRequest;
import edu.byu.model.services.request.SignUpRequest;
import edu.byu.model.services.request.StoryRequest;
import edu.byu.model.services.request.UnfollowRequest;
import edu.byu.model.services.response.FeedResponse;
import edu.byu.model.services.response.FollowResponse;
import edu.byu.model.services.response.FollowersResponse;
import edu.byu.model.services.response.FollowingResponse;
import edu.byu.model.services.response.LoginResponse;
import edu.byu.model.services.response.StatusResponse;
import edu.byu.model.services.response.SignInResponse;
import edu.byu.model.services.response.SignOutResponse;
import edu.byu.model.services.response.SignUpResponse;
import edu.byu.model.services.response.StoryResponse;
import edu.byu.model.services.response.UnfollowResponse;

/**
 * Acts as a Facade to the Tweeter server. All network requests to the server should go through
 * this class.
 */
public class ServerFacade {

    // TODO: Set this to the invoke URL of your API. Find it by going to your API in AWS, clicking
    //  on stages in the right-side menu, and clicking on the stage you deployed your API to.
    private static final String SERVER_URL = "https://c2g27qpj87.execute-api.us-west-2.amazonaws.com/Dev";

    private final ClientCommunicator clientCommunicator = new ClientCommunicator(SERVER_URL);

    /**
     * Performs a login and if successful, returns the logged in user and an auth token.
     *
     * @param request contains all information needed to perform a login.
     * @return the login response.
     */
    public LoginResponse login(LoginRequest request, String urlPath) throws IOException, TweeterRemoteException {
        LoginResponse response = clientCommunicator.doPost(urlPath, request, null, LoginResponse.class);

        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request.
     *
     * @param request contains information about the user whose followees are to be returned and any
     *                other information required to satisfy the request.
     * @return the followees.
     */
    public FollowingResponse getFollowees(FollowingRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        FollowingResponse response = clientCommunicator.doPost(urlPath, request, null, FollowingResponse.class);

        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /***
     * SignIn Feature
     */
    public SignInResponse getSignInResponse(SignInRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        SignInResponse response = clientCommunicator.doPost(urlPath, request, null, SignInResponse.class);

        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /***
     * Feed Feature
     */
    public FeedResponse getFeedStatuses(FeedRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        FeedResponse response = clientCommunicator.doPost(urlPath, request, null, FeedResponse.class);


        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Followers Feature
     */
    public FollowersResponse getFollowers(FollowersRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        FollowersResponse response = clientCommunicator.doPost(urlPath, request, null, FollowersResponse.class);

        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Follow Feature
     */
    public FollowResponse submitFollowRequest(FollowRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        FollowResponse response = clientCommunicator.doPost(urlPath, request, null, FollowResponse.class);

        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * SignOut Feature
     */
    public SignOutResponse getSignOutResponse(SignOutRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        SignOutResponse response = clientCommunicator.doPost(urlPath, request, null, SignOutResponse.class);

        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * SignUp Feature
     */
    public SignUpResponse getSignUpResponse(SignUpRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        SignUpResponse response = clientCommunicator.doPost(urlPath, request, null, SignUpResponse.class);

        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Status Feature
     */
    public StatusResponse getPostStatusResponse(StatusRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        StatusResponse response = clientCommunicator.doPost(urlPath, request, null, StatusResponse.class);

        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * Story Feature
     */
    public StoryResponse getStoryStatuses(StoryRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        StoryResponse response = clientCommunicator.doPost(urlPath, request, null, StoryResponse.class);

        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }

    /**
     * UnFollow Feature
     */
    public UnfollowResponse submitUnfollowRequest(UnfollowRequest request, String urlPath)
            throws IOException, TweeterRemoteException {
        UnfollowResponse response = clientCommunicator.doPost(urlPath, request, null, UnfollowResponse.class);

        if (response.isSuccess()) {
            return response;
        } else {
            throw new RuntimeException(response.getMessage());
        }
    }
}

//    /**
//     * Performs a login and if successful, returns the logged in user and an auth token. The current
//     * implementation is hard-coded to return a dummy user and doesn't actually make a network
//     * request.
//     *
//     * @param request contains all information needed to perform a login.
//     * @return the login response.
//     */
////    public LoginResponse login(LoginRequest request) {
////        User user = new User("Test", "User",
////                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
////        return new LoginResponse(user, new AuthToken());
////    }
//
//    /**
//     * Returns the users that the user specified in the request is following. Uses information in
//     * the request object to limit the number of followees returned and to return the next set of
//     * followees after any that were returned in a previous request. The current implementation
//     * returns generated data and doesn't actually make a network request.
//     *
//     * @param request contains information about the user whose followees are to be returned and any
//     *                other information required to satisfy the request.
//     * @return the followees.
//     */
//    public FollowingResponse getFollowees(FollowingRequest request) {
//
//        // Used in place of assert statements because Android does not support them
//        if(BuildConfig.DEBUG) {
//            if(request.getLimit() < 0) {
//                throw new AssertionError();
//            }
//
//            if(request.getFollower() == null) {
//                throw new AssertionError();
//            }
//        }
//
//        if(followeesByFollower == null) {
//            followeesByFollower = initializeFollowees();
//        }
//
//        List<User> allFollowees = followeesByFollower.get(request.getFollower());
//        List<User> responseFollowees = new ArrayList<>(request.getLimit());
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            if (allFollowees != null) {
//                int followeesIndex = getFolloweesStartingIndex(request.getLastFollowee(), allFollowees);
//
//                for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
//                    responseFollowees.add(allFollowees.get(followeesIndex));
//                }
//
//                hasMorePages = followeesIndex < allFollowees.size();
//            }
//        }
//
//        return new FollowingResponse(responseFollowees, hasMorePages);
//    }
//
//    /**
//     * Determines the index for the first followee in the specified 'allFollowees' list that should
//     * be returned in the current request. This will be the index of the next followee after the
//     * specified 'lastFollowee'.
//     *
//     * @param lastFollowee the last followee that was returned in the previous request or null if
//     *                     there was no previous request.
//     * @param allFollowees the generated list of followees from which we are returning paged results.
//     * @return the index of the first followee to be returned.
//     */
//    private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {
//
//        int followeesIndex = 0;
//
//        if(lastFollowee != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allFollowees.size(); i++) {
//                if(lastFollowee.equals(allFollowees.get(i))) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    followeesIndex = i + 1;
//                }
//            }
//        }
//
//        return followeesIndex;
//    }
//
//    //Generates the followee data.
//    private Map<User, List<User>> initializeFollowees() {
//
//        Map<User, List<User>> followeesByFollower = new HashMap<>();
//
//        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(100,
//                0, 50, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);
//
//        // Populate a map of followees, keyed by follower so we can easily handle followee requests
//        for(Follow follow : follows) {
//            List<User> followees = followeesByFollower.get(follow.getFollower());
//
//            if(followees == null) {
//                followees = new ArrayList<>();
//                followeesByFollower.put(follow.getFollower(), followees);
//            }
//
//            followees.add(follow.getFollowee());
//        }
//
//        return followeesByFollower;
//    }

//    /***
//     * Followers Feature
//     * @param request
//     * @return
//     */
//    public FollowersResponse getFollowers(FollowersRequest request) {
//
//        // Used in place of assert statements because Android does not support them
//        if(BuildConfig.DEBUG) {
//            if(request.getLimit() < 0) {
//                throw new AssertionError();
//            }
//
//            if(request.getFollower() == null) {
//                throw new AssertionError();
//            }
//        }
//
//        if(followersOfUser == null) {
//            followersOfUser = initializeFollowers();
//        }
//
//        List<User> allFollowers = followersOfUser.get(request.getFollower());
//        List<User> responseFollowers = new ArrayList<>(request.getLimit());
//
//        boolean hasMorePages = false;
//
//        if(request.getLimit() > 0) {
//            if (allFollowers != null) {
//                int followersIndex = getFollowersStartingIndex(request.getLastFollower(), allFollowers);
//
//                for(int limitCounter = 0; followersIndex < allFollowers.size() && limitCounter < request.getLimit(); followersIndex++, limitCounter++) {
//                    responseFollowers.add(allFollowers.get(followersIndex));
//                }
//
//                hasMorePages = followersIndex < allFollowers.size();
//            }
//        }
//
//        return new FollowersResponse(responseFollowers, hasMorePages);
//    }
//
//    private int getFollowersStartingIndex(User lastFollower, List<User> allFollowers) {
//
//        int followersIndex = 0;
//
//        if(lastFollower != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allFollowers.size(); i++) {
//                if(lastFollower.equals(allFollowers.get(i))) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    followersIndex = i + 1;
//                }
//            }
//        }
//
//        return followersIndex;
//    }
//
//    private Map<User, List<User>> initializeFollowers() {
//
//        Map<User, List<User>> followersOfFollowee = new HashMap<>();
//
//        List<Follow> follows = getFollowGenerator().generateUsersAndFollows(100,
//                0, 50, FollowGenerator.Sort.FOLLOWEE_FOLLOWER);
//
//        // Populate a map of followers, keyed by followee so we can easily handle follower requests
//        for(Follow follow : follows) {
//            List<User> followers = followersOfFollowee.get(follow.getFollowee());
//
//            if(followers == null) {
//                followers = new ArrayList<>();
//                followersOfFollowee.put(follow.getFollowee(), followers);
//            }
//
//            followers.add(follow.getFollower());
//        }
//
//        return followersOfFollowee;
//    }
//
//    FollowGenerator getFollowGenerator() {
//        return FollowGenerator.getInstance();
//    }


//    /***
//     * SignIn Feature
//     * @param request
//     * @return
//     */
//    public SignInResponse getSignedInUserServerResponse(SignInRequest request){
//        SignInResponse response;
//        User currentUser = SignInService.getInstance().getCurrentUser();
//
//        if (currentUser != null && request.getUserAlias().equals(currentUser.getAlias())) {
//            response = new SignInResponse(currentUser);
//        }
//        else{
//            String message = "Error: Invalid User";
//            response = new SignInResponse(message);
//        }
//        return response;
//    }

//    /***
//     * SignUp Feature
//     * @param request
//     * @return
//     */
//    public SignUpResponse getSignedUpUserServerResponse(SignUpRequest request){
//        SignUpResponse response;
//
//        try {
//            User user = new User(request.getFirstName(), request.getLastName(), request.getUserAlias(),
//                    "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//            response = new SignUpResponse(user);
//            SignInService.getInstance().setCurrentUser(user);
//        }
//        catch (Exception e){
//            String message = "Error: Invalid User";
//            response = new SignUpResponse(message);
//        }
//        return response;
//    }

//    /***
//     * SignOut Feature
//     * @param request
//     * @return
//     */
//    public SignOutResponse getSignedOutUserServerResponse(SignOutRequest request){
//        SignOutResponse response;
//        User currentUser = SignInService.getInstance().getCurrentUser();
//        response = new SignOutResponse(currentUser);
//        return response;
//    }

//    /***
//     * Status Feature
//     * @param request
//     * @return
//     */
//    public StatusResponse submitStatusRequest(StatusRequest request){
//        Status status = new Status(request.getAuthor(), request.getMessage(),
//                request.getPostTimeStamp());
//
//        if (statusesByUserMap == null)
//            statusesByUserMap = new HashMap<>();
//
//        if (statusesByUserMap.containsKey(status.getAuthor()))
//            statusesByUserMap.get(status.getAuthor()).add(status);
//        else{
//            List<Status> list = new ArrayList<>();
//            list.add(status);
//            statusesByUserMap.put(status.getAuthor(), list);
//        }
//
//        StatusResponse response = new StatusResponse(true);
//        return response;
//    }

//    /***
//     * Feed Feature
//     * @param request
//     * @return
//     */
//    public FeedResponse getFeedStatuses(FeedRequest request){
//        boolean hasMorePages = false;
//
//        // Used in place of assert statements because Android does not support them
//        if(BuildConfig.DEBUG) {
//            if(request.getLimit() < 0) {
//                throw new AssertionError();
//            }
//
//            if(request.getCurrentUser() == null) {
//                throw new AssertionError();
//            }
//        }
//
//        if (statusesByUserMap == null)
//            statusesByUserMap = new HashMap<>();
//
//        List<Status> allStatuses = new ArrayList<>();
//        try {
//            for (User user : followeesByFollower.get(request.getCurrentUser())) {
//                for (Status status : statusesByUserMap.get(user)) {
//                    allStatuses.add(status);
//                }
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        List<Status> responseStatuses = new ArrayList<>(request.getLimit());
//
//        if(request.getLimit() > 0) {
//            if (allStatuses != null) {
//                int statusesIndex = getStatusesStartingIndex(request.getLastStatus(), allStatuses);
//
//                for(int limitCounter = 0; statusesIndex < allStatuses.size()
//                        && limitCounter < request.getLimit(); statusesIndex++, limitCounter++) {
//                    responseStatuses.add(allStatuses.get(statusesIndex));
//                }
//
//                hasMorePages = statusesIndex < allStatuses.size();
//            }
//        }
//
//        return new FeedResponse(responseStatuses, hasMorePages);
//    }
//
//    private int getStatusesStartingIndex(Status lastStatus, List<Status> allStatuses) {
//
//        int statusesIndex = 0;
//
//        if(lastStatus != null) {
//            // This is a paged request for something after the first page. Find the first item
//            // we should return
//            for (int i = 0; i < allStatuses.size(); i++) {
//                if(lastStatus.equals(allStatuses.get(i))) {
//                    // We found the index of the last item returned last time. Increment to get
//                    // to the first one we should return
//                    statusesIndex = i + 1;
//                }
//            }
//        }
//
//        return statusesIndex;
//    }

//    /***
//     * Story Feature
//     * @param request
//     * @return
//     */
//    public StoryResponse getStoryStatuses(StoryRequest request){
//        boolean hasMorePages = false;
//
//        // Used in place of assert statements because Android does not support them
//        if(BuildConfig.DEBUG) {
//            if(request.getLimit() < 0) {
//                throw new AssertionError();
//            }
//
//            if(request.getCurrentUser() == null) {
//                throw new AssertionError();
//            }
//        }
//
//        if (statusesByUserMap == null)
//            statusesByUserMap = new HashMap<>();
//
//        List<Status> allStatuses = statusesByUserMap.get(request.getCurrentUser());
//        List<Status> responseStatuses = new ArrayList<>(request.getLimit());
//
//        if(request.getLimit() > 0) {
//            if (allStatuses != null) {
//                int statusesIndex = getStatusesStartingIndex(request.getLastStatus(), allStatuses);
//
//                for(int limitCounter = 0; statusesIndex < allStatuses.size()
//                        && limitCounter < request.getLimit(); statusesIndex++, limitCounter++) {
//                    responseStatuses.add(allStatuses.get(statusesIndex));
//                }
//
//                hasMorePages = statusesIndex < allStatuses.size();
//            }
//        }
//
//        return new StoryResponse(responseStatuses, hasMorePages);
//    }

//    /***
//     * Follow Feature
//     * @param request
//     * @return
//     */
//    public FollowResponse submitFollowRequest(FollowRequest request){
//        FollowResponse response = new FollowResponse(true);
//        return response;
//    }

//    /***
//     * Unfollow Feature
//     * @param request
//     * @return
//     */
//    public UnfollowResponse submitUnfollowRequest(UnfollowRequest request){
//        UnfollowResponse response = new UnfollowResponse(true);
//        return response;
//    }
