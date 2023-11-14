package passoffTests.serverTests;

import dataAccess.Database;
import dataAccess.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalTest {
    public static void main(String[] args) {
        try {
            // Create a Database instance to manage connections
            Database db = new Database();

            // Obtain a database connection
            try (Connection connection = db.getConnection()) {
                // Create a new table if it doesn't exist
                createSampleTable(connection);

                // Insert data into the table
                insertDataIntoTable(connection, "John", 30);
                insertDataIntoTable(connection, "Alice", 25);

                // Print the data from the table
                printDataFromTable(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle any database connection or query errors here
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
            // Handle any DataAccessException errors here
        }
    }

    private static void createSampleTable(Connection connection) throws SQLException {
        // SQL statement to create a sample table
        String createTableSQL = "CREATE TABLE IF NOT EXISTS sample_table (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(50) NOT NULL," +
                "age INT" +
                ")";

        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.execute();
        }
    }

    private static void insertDataIntoTable(Connection connection, String name, int age) throws SQLException {
        // SQL statement to insert data into the table
        String insertDataSQL = "INSERT INTO sample_table (name, age) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.execute();
        }
    }

    private static void printDataFromTable(Connection connection) throws SQLException {
        // SQL statement to select data from the table
        String selectDataSQL = "SELECT * FROM sample_table";

        try (PreparedStatement statement = connection.prepareStatement(selectDataSQL)) {
            ResultSet resultSet = statement.executeQuery();

            // Process and display data
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            }
        }

        // After printing data, delete all records from the table
        String deleteAllDataSQL = "DROP table sample_table";
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteAllDataSQL)) {
            deleteStatement.executeUpdate();
        }
    }
}