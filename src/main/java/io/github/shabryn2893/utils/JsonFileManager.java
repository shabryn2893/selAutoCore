package io.github.shabryn2893.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;
import org.slf4j.Logger;

/**
 * Utility class for managing JSON files. Provides methods to read,
 * retrieve, and update values in JSON files.
 */
public class JsonFileManager {

	private static final Logger logger = LoggerUtils.getLogger(JsonFileManager.class);

    // Private constructor to prevent instantiation
    private JsonFileManager() {}

    /**
     * Reads the content of a JSON file and returns it as a string.
     *
     * @param filePath the path to the JSON file.
     * @return the JSON content as a string, or an empty string if an error occurs.
     */
    public static String generateStringPayload(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            logger.error("Error reading JSON file:{} {}" ,filePath, e.getMessage());
            return ""; // Return an empty string if an error occurs
        }
    }

    /**
     * Retrieves the value associated with the specified key from the JSON file.
     *
     * @param filePath the path to the JSON file.
     * @param keyName  the key whose value needs to be retrieved.
     * @return the value associated with the key as a string, or an empty string if the key is not found or an error occurs.
     */
    public static String getJsonData(String filePath, String keyName) {
        String data = generateStringPayload(filePath);
        if (!data.isEmpty()) {
            try {
                JSONObject jsonData = new JSONObject(data);
                return jsonData.optString(keyName, ""); // Return an empty string if the key is not found
            } catch (Exception e) {
                logger.error("Error parsing JSON data from file: {}{}" ,filePath, e.getMessage());
            }
        }
        return ""; // Return an empty string if an error occurs
    }

    /**
     * Updates the value associated with the specified key in the JSON file and saves the file.
     *
     * @param filePath the path to the JSON file.
     * @param keyName  the key whose value needs to be updated.
     * @param value    the new value to be set.
     */
    public static void setJsonData(String filePath, String keyName, String value) {
        String data = generateStringPayload(filePath);
        if (!data.isEmpty()) {
            try {
                JSONObject jsonData = new JSONObject(data);
                jsonData.put(keyName, value);
                String updatedData = jsonData.toString(4); // Pretty print with 4-space indentation
                Files.write(Paths.get(filePath), updatedData.getBytes());
                logger.info("Updated {} in JSON file: {}", keyName, filePath);
            } catch (IOException e) {
                logger.error("Error writing to JSON file:{} {}" ,filePath, e.getMessage());
            }
        }
    }
}
