package io.github.shabryn2893.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;

/**
 * Utility class for performing read and write operations on properties files.
 * This class provides static methods to load, read, write, and remove
 * properties from a specified properties file.
 * 
 * Example usage: String filePath = "config.properties";
 * 
 * // Get a property String dbUser = PropertyManager.getProperty(filePath,
 * "db.user");
 * 
 * // Set a property PropertyManager.setProperty(filePath, "db.password",
 * "mySecretPassword");
 * 
 * // Remove a property PropertyManager.removeProperty(filePath, "db.password");
 * 
 */
public class PropertyFileManager {

	// Logger for this class
	private static final Logger logger = LoggerUtils.getLogger(PropertyFileManager.class);

	/**
	 * Private constructor to prevent instantiation of this utility class. Throws an
	 * {@link UnsupportedOperationException} if attempted to be instantiated.
	 */
	private PropertyFileManager() {
		throw new UnsupportedOperationException("Utility class");
	}

	/**
	 * Loads properties from the specified file path.
	 * 
	 * @param filePath the path to the properties file.
	 * @return a {@link Properties} object containing the properties loaded from the
	 *         file, or an empty {@link Properties} object if an error occurs.
	 */
	public static Properties loadProperties(String filePath) {
		Properties properties = new Properties();
		try (FileInputStream inputStream = new FileInputStream(filePath)) {
			properties.load(inputStream);
			logger.info("Properties loaded successfully from file: {}", filePath);
		} catch (IOException e) {
			// Log the error
			logger.error("Error loading properties from file: {}", filePath);
		}
		return properties;
	}

	/**
	 * Retrieves the value of a specified property from the given properties file.
	 * 
	 * @param filePath the path to the properties file.
	 * @param key      the key of the property to retrieve.
	 * @return the value of the specified property, or an empty string if the key is
	 *         not found or an error occurs.
	 */
	public static String getProperty(String filePath, String key) {
		Properties properties = loadProperties(filePath);
		String value = properties.getProperty(key, "");
		if (value.isEmpty()) {
			logger.error("Property {} not found in file: {}", key, filePath);
		} else {
			logger.info("Property {} retrieved from file: {}", key, filePath);
		}
		return value;
	}

	/**
	 * Sets or updates the value of a specified property in the given properties
	 * file. If the property does not exist, it will be created.
	 * 
	 * @param filePath the path to the properties file.
	 * @param key      the key of the property to set or update.
	 * @param value    the value to set for the specified property.
	 */
	public static void setProperty(String filePath, String key, String value) {
		Properties properties = loadProperties(filePath);
		properties.setProperty(key, value);
		saveProperties(filePath, properties);
		logger.info("Property {} set to {} in file: {}", key, value, filePath);
	}

	/**
	 * Saves the properties to the specified file path.
	 * 
	 * @param filePath   the path to the properties file.
	 * @param properties the {@link Properties} object to save.
	 */
	public static void saveProperties(String filePath, Properties properties) {
		try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
			properties.store(outputStream, null); // You can add a comment as the second parameter
			logger.info("Properties saved successfully to file: {}", filePath);
		} catch (IOException e) {
			// Log the error
			logger.error("Error saving properties to file: {}", filePath);
		}
	}

	/**
	 * Removes a specified property from the given properties file.
	 * 
	 * @param filePath the path to the properties file.
	 * @param key      the key of the property to remove.
	 */
	public static void removeProperty(String filePath, String key) {
		Properties properties = loadProperties(filePath);
		if (properties.remove(key) != null) {
			saveProperties(filePath, properties);
			logger.info("Property {} removed from file: {}", key, filePath);
		} else {
			logger.error("Property {} not found in file: {}", key, filePath);
		}
	}

}
