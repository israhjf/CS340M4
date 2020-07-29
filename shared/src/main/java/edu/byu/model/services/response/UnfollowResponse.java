package edu.byu.model.services.response;

public class UnfollowResponse extends Response{
    public Boolean isSuccesful;
    public String errorMessage;

    public UnfollowResponse(Boolean isSuccesful){
        super(true);
        this.isSuccesful = isSuccesful;
    }

    public UnfollowResponse(String errorMessage) {
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
