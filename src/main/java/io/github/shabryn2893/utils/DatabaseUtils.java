package io.github.shabryn2893.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for handling database operations such as connection management,
 * executing CRUD operations, and resource management.
 */
public class DatabaseUtils {

	private static final Logger LOGGER = Logger.getLogger(DatabaseUtils.class.getName());
	private static String dbUrl;
	private static String userName;
	private static String password;

	// Private constructor to prevent instantiation
	private DatabaseUtils() {
		throw new UnsupportedOperationException("DatabaseUtils class should not be instantiated");
	}

	/**
	 * @return the dbUrl
	 */
	public static String getDbUrl() {
		return dbUrl;
	}

	/**
	 * @param dBUrl the dbUrl to set
	 */
	public static void setDbUrl(String dBUrl) {
		dbUrl = dBUrl;
	}

	/**
	 * @return the uSERNAME
	 */
	public static String getUserName() {
		return userName;
	}

	/**
	 * @param dbUserName the userName to set
	 */
	public static void setUserName(String dbUserName) {
		userName = dbUserName;
	}

	/**
	 * @return the pASSWORD
	 */
	public static String getPassword() {
		return password;
	}

	/**
	 * @param password the dbPassword to set
	 */
	public static void setPassword(String dbPassword) {
		password = dbPassword;
	}

	/**
	 * Establishes a connection to the database.
	 * 
	 * @return A {@link Connection} object or null if the connection fails.
	 */
	public static Connection connect() {
		try {
			return DriverManager.getConnection(getDbUrl(), getUserName(), getPassword());
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Failed to connect to the database.", e);
			return null;
		}
	}

	/**
	 * Closes the provided connection.
	 *
	 * @param connection The {@link Connection} to be closed. Can be null.
	 */
	public static void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "Failed to close connection.", e);
			}
		}
	}

	/**
	 * Executes an INSERT SQL query.
	 *
	 * @param query  The SQL query to execute.
	 * @param params The parameters to set in the prepared statement.
	 * @return true if the operation was successful, false otherwise.
	 */
	public static boolean insert(String query, Object... params) {
		return executeUpdate(query, params);
	}

	/**
	 * Executes a SELECT SQL query.
	 *
	 * @param query  The SQL query to execute.
	 * @param params The parameters to set in the prepared statement.
	 * @return A {@link ResultSet} containing the results, or null if the query
	 *         fails.
	 */
	public static ResultSet select(String query, Object... params) {
		Connection connection = connect();
		if (connection == null)
			return null;

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			setParameters(preparedStatement, params);
			return preparedStatement.executeQuery();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Failed to execute select query.", e);
			closeConnection(connection);
			return null;
		}
	}

	/**
	 * Executes an UPDATE SQL query.
	 *
	 * @param query  The SQL query to execute.
	 * @param params The parameters to set in the prepared statement.
	 * @return true if the operation was successful, false otherwise.
	 */
	public static boolean update(String query, Object... params) {
		return executeUpdate(query, params);
	}

	/**
	 * Executes a DELETE SQL query.
	 *
	 * @param query  The SQL query to execute.
	 * @param params The parameters to set in the prepared statement.
	 * @return true if the operation was successful, false otherwise.
	 */
	public static boolean delete(String query, Object... params) {
		return executeUpdate(query, params);
	}

	/**
	 * Executes an INSERT, UPDATE, or DELETE SQL query.
	 *
	 * @param query  The SQL query to execute.
	 * @param params The parameters to set in the prepared statement.
	 * @return true if the operation was successful, false otherwise.
	 */
	private static boolean executeUpdate(String query, Object... params) {
		Connection connection = connect();
		if (connection == null)
			return false;

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			setParameters(preparedStatement, params);
			return preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Failed to execute update query.", e);
			return false;
		} finally {
			closeConnection(connection);
		}
	}

	/**
	 * Sets the parameters for a prepared statement.
	 *
	 * @param preparedStatement The {@link PreparedStatement} to set parameters for.
	 * @param params            The parameters to set in the prepared statement.
	 * @throws SQLException If an SQL exception occurs while setting parameters.
	 */
	private static void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
		for (int i = 0; i < params.length; i++) {
			preparedStatement.setObject(i + 1, params[i]);
		}
	}
}
