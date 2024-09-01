package io.github.shabryn2893.selAutoCore.apiCore;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIActionsRestAssured implements IActionAPI {

	RequestSpecification request;
	Response response;

	public APIActionsRestAssured(String baseURI) {
		RestAssured.baseURI = baseURI;
		request = RestAssured.given();
	}

	@Override
	public Response getRequest(String resourceName) {
		this.setContentType("JSON");
		return response = request.get(resourceName);

	}

	@Override
	public Response getRequest(String resourceName, String bToken) {
		this.setContentType("JSON");
		this.setHeader("Authorization", "Bearer " + bToken);
		return response = request.get(resourceName);

	}

	@Override
	public Response getRequest(String resourceName, Map<String, String> params) {
		this.setContentType("JSON");
		this.setParams(params);
		return response = request.get(resourceName);
	}

	@Override
	public Response getRequest(String resourceName, Map<String, String> params, String bToken) {
		this.setContentType("JSON");
		this.setParams(params);
		this.setHeader("Authorization", "Bearer " + bToken);
		return response = request.get(resourceName);
	}

	@Override
	public Response postRequest(String resourceName, String payLoad) {
		this.setContentType("JSON");
		request.body(payLoad);
		return response = request.post(resourceName);
	}

	@Override
	public Response postRequest(String resourceName, String payLoad, String bToken) {
		this.setContentType("JSON");
		this.setHeader("Authorization", "Bearer " + bToken);
		request.body(payLoad);
		return response = request.post(resourceName);
	}

	@Override
	public Response putRequest(String resourceName, String payLoad) {
		this.setContentType("JSON");
		request.body(payLoad);
		return response = request.put(resourceName);
	}

	@Override
	public Response putRequest(String resourceName, String payLoad, String bToken) {
		this.setContentType("JSON");
		this.setHeader("Authorization", "Bearer " + bToken);
		request.body(payLoad);
		return response = request.put(resourceName);
	}

	@Override
	public Response patchRequest(String resourceName, String payLoad) {
		this.setContentType("JSON");
		request.body(payLoad);
		return response = request.patch(resourceName);
	}

	@Override
	public Response patchRequest(String resourceName, String payLoad, String bToken) {
		this.setContentType("JSON");
		this.setHeader("Authorization", "Bearer " + bToken);
		request.body(payLoad);
		return response = request.patch(resourceName);
	}

	@Override
	public Response deleteRequest(String resourceName) {
		return response = request.delete(resourceName);
	}

	@Override
	public Response deleteRequest(String resourceName, String bToken) {
		this.setHeader("Authorization", "Bearer " + bToken);
		return response = request.delete(resourceName);
	}

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

	@Override
	public String getContentType() {
		return response.getContentType();
	}

	@Override
	public String getHeader(String headerName) {
		// TODO Auto-generated method stub
		return response.getHeader(headerName);
	}

	@Override
	public void setHeader(String headerName, String headerValue) {
		request.header(headerName, headerValue);

	}

	@Override
	public int getStatusCode() {
		return response.getStatusCode();
	}

	@Override
	public String getErrorMessage() {
		return response.getStatusLine();
	}

	@Override
	public void setParams(Map<String, String> params) {
		request.queryParams(params);
	}

	@Override
	public void printResponse() {
		System.out.println(response.asPrettyString());
	}

	@Override
	public JSONObject parseResponseJsonObject() {
		JSONObject JSONObjectResponseBody = new JSONObject(response.body().asString());
		return JSONObjectResponseBody;
	}

	@Override
	public JSONArray parsResponseJsonArray() {
		JSONArray JSONArrayResponseBody = new JSONArray(response.body().asString());
		return JSONArrayResponseBody;
	}

}
