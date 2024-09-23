package io.github.shabryn2893.tests.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.annotations.Test;

import io.github.shabryn2893.utils.DatabaseUtils;

public class TestDatabaseUtils {

	@Test
	public void testDB() {
		
		//Setting up Data base details.
		DatabaseUtils.setDbUrl("jdbc:mysql://localhost:3306/qatest");
		DatabaseUtils.setUserName("root");	
		DatabaseUtils.setPassword("Admin2893#");
		
		// Example of inserting data
        String insertQuery = "INSERT INTO users (name, email) VALUES (?, ?)";
        DatabaseUtils.insert(insertQuery, "John Doe", "john@example.com");

        // Example of selecting data
        String selectQuery = "SELECT * FROM users WHERE email = ?";
        ResultSet resultSet = DatabaseUtils.select(selectQuery, "john@example.com");
        try {
            while (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("name"));
                System.out.println("Email: " + resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Example of updating data
        String updateQuery = "UPDATE users SET name = ? WHERE email = ?";
        DatabaseUtils.update(updateQuery, "John Doe Updated", "john@example.com");

        // Example of deleting data
        String deleteQuery = "DELETE FROM users WHERE email = ?";
        DatabaseUtils.delete(deleteQuery, "john@example.com");

	}

}