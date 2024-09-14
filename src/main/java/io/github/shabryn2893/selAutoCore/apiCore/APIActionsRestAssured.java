package io.github.shabryn2893.selAutoCore.apiCore;

import java.io.File;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIActionsRestAssured implements IActionAPI {

	RequestSpecification request;
	Response response;

	public APIActionsRestAssured(String baseURI) {
		RestAssured.baseURI = baseURI;
		request = RestAssured.given();
	}

	/**
     * Perform a GET request to a <code>path</code>.
     *
     * @param resourceName       The path to send the request to.
     * @return The response of the request.
     */
	@Override
	public Response getRequest(String resourceName) {
		return response = request.get(resourceName);
	}

	/**
     * Perform a POST request to a <code>path</code>.
     *
     * @param resourceName       The path to send the request to.
     * @param payLoad request body to be send.
     * @return The response of the request.
     */
	@Override
	public Response postRequest(String resourceName, String payLoad) {
		request.body(payLoad);
		return response = request.post(resourceName);
	}

	/**
     * Perform a PUT request to a <code>path</code>.
     *
     * @param resourceName       The path to send the request to.
     * @param payLoad request body to be send.
     * @return The response of the request.
     */
	@Override
	public Response putRequest(String resourceName, String payLoad) {
		request.body(payLoad);
		return response = request.put(resourceName);
	}

	/**
     * Perform a PATCH request to a <code>path</code>.
     *
     * @param resourceName       The path to send the request to.
     * @param payLoad request body to be send.
     * @return The response of the request.
     */
	@Override
	public Response patchRequest(String resourceName, String payLoad) {
		request.body(payLoad);
		return response = request.patch(resourceName);
	}

	/**
     * Perform a DELETE request to a <code>path</code>.
     *
     * @param resourceName       The path to send the request to.
     * @return The response of the request.
     */
	@Override
	public Response deleteRequest(String resourceName) {
		return response = request.delete(resourceName);
	}

	/**
     * Specify the content type of the request.
     *
     * @param contentType The content type of the request
     * @return void
     */
	@Override
	public void setContentType(String contectType) {
		switch (contectType.toUpperCase()) {
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
			System.out.println("Unsupported Content type: " + contectType);
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
     * Get a single header value associated with the given name. If the header is a multi-value header then you need to use
     * {@link Headers#getList(String)} in order to get all values..
     *
     * @return The header value or <code>null</code> if value was not found.
     */
	@Override
	public String getHeader(String headerName) {
		// TODO Auto-generated method stub
		return response.getHeader(headerName);
	}

	/**
     * Specify a header that'll be sent with the request e.g:
     * @param headerName             The header name
     * @param headerValue            The header value
     * @return void
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
     * Specify the query parameters that'll be sent with the request. Note that this method is the same as {@link #params(String, Object, Object...)}
     * for all http methods except for POST where {@link #params(String, Object, Object...)} sets the form parameters and this method sets the
     * query parameters.
     *
     * @param paramKey      The name of the parameter
     * @param paramValue     The value of the parameter
     * @return void
     */
	@Override
	public void setParam(String paramKey,String paramValue) {
		request.queryParam(paramKey, paramValue);
	}
	
	/**
     * Specify the query parameters that'll be sent with the request. Note that this method is the same as {@link #params(Map)}
     * for all http methods except for POST where {@link #params(Map)} sets the form parameters and this method sets the
     * query parameters.
     *
     * @param params The Map containing the parameter names and their values to send with the request.
     * @return void
     */
	@Override
	public void setParams(Map<String, String> params) {
		request.queryParams(params);
	}

	@Override
	public void printResponse() {
		System.out.println(response.asPrettyString());
	}

	/**
     * Parse response as JSONObject
     * @return JSONObject
     */
	@Override
	public JSONObject parseResponseJsonObject() {
		JSONObject JSONObjectResponseBody = new JSONObject(response.body().asString());
		return JSONObjectResponseBody;
	}

	/**
     * Parse response as JSONArray
     * @return JSONArray
     */
	@Override
	public JSONArray parsResponseJsonArray() {
		JSONArray JSONArrayResponseBody = new JSONArray(response.body().asString());
		return JSONArrayResponseBody;
	}
	/**
     * Specify a file to upload to the server using multi-part form data uploading with a specific
     * control name. It will use the mime-type <tt>application/octet-stream</tt>.
     * If this is not what you want please use an overloaded method.
     *
     * @param file        The file to upload
     * @param controlName Defines the control name of the body part. In HTML this is the attribute name of the input tag.
     * @return void
     */
	public void setMultiPartAttribute(String controlName, File file) {
		request.multiPart(controlName,file);
	}

}
