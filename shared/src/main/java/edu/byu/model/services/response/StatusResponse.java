package edu.byu.model.services.response;

public class StatusResponse extends Response{
    public boolean isSuccessful;
    public String errorMessage;

    public StatusResponse(String errorMessage) {
        super(false);
        this.errorMessage = errorMessage;
    }

    public StatusResponse(boolean isSuccessful) {
        super(true);
        this.isSuccessful = isSuccessful;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
