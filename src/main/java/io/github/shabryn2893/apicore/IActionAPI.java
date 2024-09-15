package io.github.shabryn2893.apicore;

import java.io.File;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.response.Response;

/**
 * Reusable functions across framework
 * 
 * @author shabbir rayeen
 */
public interface IActionAPI {

	/**
	 * Perform a GET request to a <code>path</code>.
	 *
	 * @param resourceName The path to send the request to.
	 * @return The response of the request.
	 */
	public Response getRequest(String resourceName);

	/**
	 * Perform a POST request to a <code>path</code>.
	 *
	 * @param resourceName The path to send the request to.
	 * @param payLoad      request body to be send.
	 * @return The response of the request.
	 */
	public Response postRequest(String resourceName, String payLoad);

	/**
	 * Perform a PUT request to a <code>path</code>.
	 *
	 * @param resourceName The path to send the request to.
	 * @param payLoad      request body to be send.
	 * @return The response of the request.
	 */
	public Response putRequest(String resourceName, String payLoad);

	/**
	 * Perform a PATCH request to a <code>path</code>.
	 *
	 * @param resourceName The path to send the request to.
	 * @param payLoad      request body to be send.
	 * @return The response of the request.
	 */
	public Response patchRequest(String resourceName, String payLoad);

	/**
	 * Perform a DELETE request to a <code>path</code>.
	 *
	 * @param resourceName The path to send the request to.
	 * @return The response of the request.
	 */
	public Response deleteRequest(String resourceName);

	/**
	 * Specify the content type of the request.
	 *
	 * @param contectType The content type of the request
	 */
	public void setContentType(String contectType);

	/**
	 * Get the content type of the response
	 *
	 * @return The content type value or <code>null</code> if not found.
	 */
	public String getContentType();

	/**
	 * Get a single header value associated with the given name.
	 * 
	 * @param headerName header name value
	 *
	 * @return The header value or null if value was not found.
	 */
	public String getHeader(String headerName);

	/**
	 * Specify a header that'll be sent with the request e.g:
	 * 
	 * @param headerName  The header name
	 * @param headerValue The header value
	 */
	public void setHeader(String headerName, String headerValue);

	/**
	 * Get the status code of the response.
	 *
	 * @return The status code of the response.
	 */
	public int getStatusCode();

	/**
	 * Get the status line of the response.
	 *
	 * @return The status line of the response.
	 */
	public String getErrorMessage();

	/**
	 * Specify the query parameters that'll be sent with the request.
	 *
	 * @param paramKey   The name of the parameter
	 * @param paramValue The value of the parameter
	 */
	public void setParam(String paramKey, String paramValue);

	/**
	 * Specify the query parameters that'll be sent with the request.
	 *
	 * @param params The Map containing the parameter names and their values to send
	 *               with the request.
	 */
	public void setParams(Map<String, String> params);

	/**
	 * Prints the API response
	 * 
	 *
	 */
	public void printResponse();

	/**
	 * Parse response as JSONObject
	 * 
	 * @return JSONObject
	 */
	public JSONObject parseResponseJsonObject();

	/**
	 * Parse response as JSONArray
	 * 
	 * @return JSONArray
	 */
	public JSONArray parsResponseJsonArray();

	/**
	 * Specify a file to upload to the server using multi-part form data uploading
	 * with a specific control name. It will use the mime-type.
	 *
	 * @param file        The file to upload
	 * @param controlName Defines the control name of the body part. In HTML this is
	 *                    the attribute name of the input tag.
	 */
	public void setMultiPartAttribute(String controlName, File file);
}
