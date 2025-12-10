package net.metallaxis.database;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationClient {

    // Base URL for the FastAPI service
    private static final String BASE_URL = "https://api.metallaxis.net/tic-tac-toe";
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    
    // Store the token globally for use in protected calls (like locating a user)
    private static String currentAccessToken = null;

    // --- Helper to build JSON body ---
    // NOTE: In a professional app, use Jackson or Gson for safe JSON handling.
    private static String buildJson(String username, String email, String password) {
        if (email != null) {
            return String.format(
                "{\"username\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}",
                username, email, password);
        } else {
            return String.format(
                "{\"username\": \"%s\", \"password\": \"%s\"}",
                username, password);
        }
    }

    // =========================================================================
    // REPLACEMENT FOR addUser() (Registration)
    // =========================================================================
    // Original: INSERT INTO `users`
    // New: POST to /addUser
    public static boolean addUser(String username, String email, String password) {
        String jsonBody = buildJson(username, email, password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/addUser"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) { // 201 Created is the success code
                System.out.println("User added successfully!");
                return true;
            } else if (response.statusCode() == 409) { // 409 Conflict (User exists)
                System.err.println("Registration failed: User already exists!");
                return false;
            } else {
                // Log detailed error from FastAPI for debugging
                System.err.println("Registration failed. Status: " + response.statusCode() + " Detail: " + response.body());
                return false;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("API Connection Error during addUser: " + e.getMessage());
            return false;
        }
    }

    // =========================================================================
    // REPLACEMENT FOR findUser() (Login)
    // =========================================================================
    // Original: SELECT * FROM users WHERE username=? AND password=?
    // New: POST to /login. If successful, stores the token and returns a User.
    public static User login(String username, String password) {
        String loginBody = buildJson(username, null, password); // Email is null for login

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(loginBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // SUCCESS: Parse the JSON response for the token
                String responseBody = response.body();
                
                // Simple regex to extract token from: {"access_token":"TOKEN","token_type":"bearer"}
                Pattern p = Pattern.compile("\"access_token\":\"(.*?)\"");
                Matcher m = p.matcher(responseBody);

                if (m.find()) {
                    currentAccessToken = m.group(1);
                    System.out.println("Login successful. Token acquired.");
                    
                    // The API doesn't return the email on login, so we must fetch details
                    return locateUser(username); // Uses the new locateUser (protected call)
                }
            } else {
                System.err.println("Login failed. Status: " + response.statusCode() + " Detail: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("API Connection Error during login: " + e.getMessage());
        }
        return null; // Login failed
    }

    // =========================================================================
    // REPLACEMENT FOR locateUser()
    // =========================================================================
    // Original: SELECT * FROM users WHERE username=?
    // New: GET to /locateUser/{username} with the JWT token
    public static User locateUser(String username) {
        // If this is called after successful login, the token is set.
        if (currentAccessToken == null) {
            System.err.println("Error: Cannot locate user; must be logged in first to get token.");
            return null;
        }
        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/locateUser/" + username))
                .header("Authorization", "Bearer " + currentAccessToken) // Authorized call!
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                
                if (responseBody.contains("\"found\":true")) {
                    // Extract email (and username, which is George)
                    Pattern p = Pattern.compile("\"email\":\"(.*?)\"");
                    Matcher m = p.matcher(responseBody);
                    String email = m.find() ? m.group(1) : "N/A";

                    return new User(username, email, currentAccessToken);
                }
            } else {
                System.err.println("Failed to fetch user details. Status: " + response.statusCode() + " Body: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
