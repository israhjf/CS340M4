package edu.byu.model.services.response;

/**
 * A base class for server responses.
 */
class Response {

    public boolean success;
    public String message;

    /**
     * Creates an instance with a null message.
     *
     * @param success the success message.
     */
    public Response(boolean success) {
        this(success, null);
    }

    /**
     * Creates an instance.
     *
     * @param success the success indicator.
     * @param message the error message.
     */
    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Indicates whether the response represents a successful result.
     *
     * @return the success indicator.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * The error message for unsuccessful results.
     *
     * @return an error message or null if the response indicates a successful result.
     */
    public String getMessage() {
        return message;
    }
}
