package io.github.shabryn2893.apicore;

import java.io.File;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

import io.github.shabryn2893.utils.LoggerUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * This class implements all method of the IActionAPI interface.
 * 
 * @author shabbir rayeen
 */
public class APIActionsRestAssured implements IActionAPI {

	private static final Logger logger = LoggerUtils.getLogger(APIActionsRestAssured.class);
	RequestSpecification request;
	Response response;

	/**
	 * Initiates the base URI
	 *
	 * @param baseURI API base URL.
	 */
	public APIActionsRestAssured(String baseURI) {
		RestAssured.baseURI = baseURI;
		request = RestAssured.given();
	}

	/**
	 * Perform a GET request to a <code>path</code>.
	 *
	 * @param resourceName The path to send the request to.
	 * @return The response of the request.
	 */
	@Override
	public Response getRequest(String resourceName) {
		response = request.get(resourceName);
		return response;
	}

	/**
	 * Perform a POST request to a <code>path</code>.
	 *
	 * @param resourceName The path to send the request to.
	 * @param payLoad      request body to be send.
	 * @return The response of the request.
	 */
	@Override
	public Response postRequest(String resourceName, String payLoad) {
		request.body(payLoad);
		response = request.post(resourceName);
		return response;
	}

	/**
	 * Perform a PUT request to a <code>path</code>.
	 *
	 * @param resourceName The path to send the request to.
	 * @param payLoad      request body to be send.
	 * @return The response of the request.
	 */
	@Override
	public Response putRequest(String resourceName, String payLoad) {
		request.body(payLoad);
		response = request.put(resourceName);
		return response;
	}

	/**
	 * Perform a PATCH request to a <code>path</code>.
	 *
	 * @param resourceName The path to send the request to.
	 * @param payLoad      request body to be send.
	 * @return The response of the request.
	 */
	@Override
	public Response patchRequest(String resourceName, String payLoad) {
		request.body(payLoad);
		response = request.patch(resourceName);
		return response;
	}

	/**
	 * Perform a DELETE request to a <code>path</code>.
	 *
	 * @param resourceName The path to send the request to.
	 * @return The response of the request.
	 */
	@Override
	public Response deleteRequest(String resourceName) {
		response = request.delete(resourceName);
		return response;
	}

	/**
	 * Specify the content type of the request.
	 *
	 * @param contentType The content type of the request
	 */
	@Override
	public void setContentType(String contentType) {
		switch (contentType.toUpperCase()) {
		case "JSON":
			request.contentType(ContentType.JSON);
			break;
		case "MULTIPART":
			request.contentType(ContentType.MULTIPART);
			break;
		case "XML":
			request.contentType(ContentType.XML);
			break;
		case "ANY":
			request.contentType(ContentType.ANY);
			break;
		case "BINARY":
			request.contentType(ContentType.BINARY);
			break;
		case "HTML":
			request.contentType(ContentType.HTML);
			break;
		case "TEXT":
			request.contentType(ContentType.TEXT);
			break;
		default:
			 logger.error("Unsupported Content type:{}",contentType);
		}

	}

	/**
	 * Get the content type of the response
	 *
	 * @return The content type value or <code>null</code> if not found.
	 */
	@Override
	public String getContentType() {
		return response.getContentType();
	}

	/**
	 * Get a single header value associated with the given name.
	 *
	 * @return The header value or <code>null</code> if value was not found.
	 */
	@Override
	public String getHeader(String headerName) {
		return response.getHeader(headerName);
	}

	/**
	 * Specify a header that'll be sent with the request e.g:
	 * 
	 * @param headerName  The header name
	 * @param headerValue The header value
	 */
	@Override
	public void setHeader(String headerName, String headerValue) {
		request.header(headerName, headerValue);

	}

	/**
	 * Get the status code of the response.
	 *
	 * @return The status code of the response.
	 */
	@Override
	public int getStatusCode() {
		return response.getStatusCode();
	}

	/**
	 * Get the status line of the response.
	 *
	 * @return The status line of the response.
	 */
	@Override
	public String getErrorMessage() {
		return response.getStatusLine();
	}

	/**
	 * Specify the query parameters that'll be sent with the request.
	 *
	 * @param paramKey   The name of the parameter
	 * @param paramValue The value of the parameter
	 */
	@Override
	public void setParam(String paramKey, String paramValue) {
		request.queryParam(paramKey, paramValue);
	}

	/**
	 * Specify the query parameters that'll be sent with the request.
	 *
	 * @param params The Map containing the parameter names and their values to send
	 *               with the request.
	 */
	@Override
	public void setParams(Map<String, String> params) {
		request.queryParams(params);
	}

	/**
	 * Prints the API response
	 * 
	 *
	 */
	@Override
	public void printResponse() {
		logger.info("API Response: {}",response);
	}

	/**
	 * Parse response as JSONObject
	 * 
	 * @return JSONObject
	 */
	@Override
	public JSONObject parseResponseJsonObject() {
		return new JSONObject(response.body().asString());
	}

	/**
	 * Parse response as JSONArray
	 * 
	 * @return JSONArray
	 */
	@Override
	public JSONArray parsResponseJsonArray() {
		return new JSONArray(response.body().asString());
	}

	/**
	 * Specify a file to upload to the server using multi-part form data uploading
	 * with a specific control name. It will use the mime-type.
	 *
	 * @param file        The file to upload
	 * @param controlName Defines the control name of the body part. In HTML this is
	 *                    the attribute name of the input tag.
	 */
	public void setMultiPartAttribute(String controlName, File file) {
		request.multiPart(controlName, file);
	}

}
