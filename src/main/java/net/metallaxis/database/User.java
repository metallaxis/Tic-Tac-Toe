package net.metallaxis.database;

// The User object for the client application
public class User {
    private String username;
    private String email;
    private String accessToken; // Used to authorize protected requests

    // We no longer track ID or the plain password here.

    public User(String username, String email, String accessToken) {
        this.username = username;
        this.email = email;
        this.accessToken = accessToken;
    }

    // Add getters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getAccessToken() { return accessToken; }
    // ... other methods as needed
}
