package Assignment;

import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserRegistration {
    private Connection connection;

    public UserRegistration() {
        try {
            this.connection = DatabaseConnector.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to hash the password using MessageDigest
    private String hashPassword(String password) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add password bytes to digest
            md.update(password.getBytes());

            // Get the hashed bytes
            byte[] hashedBytes = md.digest();

            // Convert bytes to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean registerUser(String username, String email, String password, String role) {
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Error: Invalid email format.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String schemaName = "user"; // Change this to your actual schema name

        String checkQuery = "SELECT COUNT(*) FROM " + schemaName + "." + (role.toLowerCase()) + " WHERE " + role + "_EMAIL= ?";
        String insertQuery = "INSERT INTO " + schemaName + "." + (role.toLowerCase()) + "(" + role + "_USERNAME, " + role + "_EMAIL, " + role + "_PASSWORD) VALUES (?, ?, ?)";

        try {
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, email);
                ResultSet resultSet = checkStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    // Email already exists, return false or handle as needed
                    JOptionPane.showMessageDialog(null, "Error: Email already exists.", "Registration Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

            // Hash the password before storing it
            String hashedPassword = hashPassword(password);

            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, username);
                insertStatement.setString(2, email);
                insertStatement.setString(3, hashedPassword);

                int rowsAffected = insertStatement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateUser(String email, String password, String role) {
        String schemaName = "user";
        String query = "SELECT " + role + "_PASSWORD FROM " + schemaName + "." + role + " WHERE " + role + "_USERNAME = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString(role + "_PASSWORD");
                // Hash the provided password for comparison
                String hashedProvidedPassword = hashPassword(password);
                // Check if the provided password matches the hashed password
                return hashedPassword.equals(hashedProvidedPassword);
            } else {
                // No user with the given email found
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isValidEmail(String email) {
        // Regular expression pattern for a simple email validation
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void saveLocationCoordinate(String username, String role, String locationCoordinate) {
        String schemaName = "user";
        String checkQuery = "SELECT COUNT(*) FROM " + schemaName + "." + role.toLowerCase() + " WHERE " + role + "_USERNAME = ?";
        String updateQuery = "UPDATE " + schemaName + "." + role.toLowerCase() + " SET " + role + "_LOCATION_COORDINATE = ? WHERE " + role + "_USERNAME = ?";

        try {
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, username);
                ResultSet resultSet = checkStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setString(1, locationCoordinate);
                        updateStatement.setString(2, username);
                        updateStatement.executeUpdate();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Username not found", "Server Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

/*
MessageDigest is a class in Java that provides cryptographic hashing functionality. It allows you to generate a hash value (also known as a digest) for input data. This hash value is typically a fixed-size byte array that uniquely represents the input data. Here's how MessageDigest works:

Initialization: First, you create an instance of the MessageDigest class by calling its getInstance method, passing the desired hashing algorithm as a parameter. For example, to use the SHA-256 algorithm, you would call MessageDigest.getInstance("SHA-256").
Data Input: Once you have a MessageDigest instance, you can feed data into it by calling its update method and passing the data as a byte array. You can call update multiple times if you need to process data in chunks.
Hash Calculation: After feeding all the input data into the MessageDigest, you can calculate the hash value by calling its digest method. This method returns the hash value as a byte array.
Output: You typically convert the byte array hash value to a more readable format, such as hexadecimal or Base64 encoding, for easier handling and storage.*/