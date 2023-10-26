package Server;

import spark.*;

public class Server {

    /**
     * server default constructor
     */
    public Server() {
        String absolutePath = "C:\\Users\\kaden\\school\\23 Fall\\cs 240\\chess\\src\\web";
        Spark.externalStaticFileLocation(absolutePath);
    }

    public static void main(String[] args) {
        new Server().run();
    }

    private void run() {
        // Specify the port you want the server to listen on
        Spark.port(8080);

        // Register a user
        Spark.post("/register", (request, response) -> {
            // Your registration logic here
            return "User registered successfully";
        });

        // Log in a user
        Spark.post("/login", (request, response) -> {
            // Your login logic here
            return "User logged in successfully";
        });

        // Log out an authenticated user
        Spark.get("/logout", (request, response) -> {
            // Your logout logic here
            return "User logged out successfully";
        });

        // List all games in the database
        Spark.get("/games", (request, response) -> {
            // Your code to retrieve and list games from the database
            return "List of games in the database";
        });

        // Create a new chess game
        Spark.post("/games", (request, response) -> {
            // Your code to create a new chess game in the database
            return "New chess game created";
        });

        // Join a chess game
        Spark.post("/games/:gameId/join", (request, response) -> {
            String gameId = request.params(":gameId");
            // Your code to join the specified chess game
            return "Joined chess game " + gameId;
        });

        // Clear all data from the database (dangerous action)
        Spark.post("/clearDatabase", (request, response) -> {
            // Your code to clear all data from the database
            return "Database cleared";
        });
    }
}
