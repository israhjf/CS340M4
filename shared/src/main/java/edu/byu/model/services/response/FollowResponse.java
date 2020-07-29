package edu.byu.model.services.response;

public class FollowResponse extends Response{
    public Boolean isSuccesful;
    public String errorMessage;

    public FollowResponse(Boolean isSuccesful){
        super(true);
        this.isSuccesful = isSuccesful;
    }

    public FollowResponse(String errorMessage) {
        super(false);
        this.errorMessage = errorMessage;
    }

    public Boolean getSuccesful() {
        return isSuccesful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
