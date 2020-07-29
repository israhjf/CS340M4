package edu.byu.model.services.request;

public class SignUpRequest {
    public String userAlias;
    public String password;
    public String firstName;
    public String lastName;
    public String imageUrl;

    public SignUpRequest() {}

    public SignUpRequest(String userAlias, String password, String firstName,
                         String lastName, String imageUrl) {
        this.userAlias = userAlias;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
