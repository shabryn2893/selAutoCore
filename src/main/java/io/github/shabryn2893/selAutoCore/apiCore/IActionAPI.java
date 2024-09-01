package io.github.shabryn2893.selAutoCore.apiCore;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.response.Response;

public interface IActionAPI {

	public Response getRequest(String resourceName);

	public Response getRequest(String resourceName, String bToken);

	public Response getRequest(String resourceName, Map<String, String> params);

	public Response getRequest(String resourceName, Map<String, String> params, String bToken);

	public Response postRequest(String resourceName, String payLoad);

	public Response postRequest(String resourceName, String payLoad, String bToken);

	public Response putRequest(String resourceName, String payLoad);

	public Response putRequest(String resourceName, String payLoad, String bToken);

	public Response patchRequest(String resourceName, String payLoad);

	public Response patchRequest(String resourceName, String payLoad, String bToken);

	public Response deleteRequest(String resourceName);

	public Response deleteRequest(String resourceName, String bToken);

	public void setContentType(String contectType);

	public String getContentType();

	public String getHeader(String headerName);

	public void setHeader(String headerName, String headerValue);

	public int getStatusCode();

	public String getErrorMessage();

	public void setParams(Map<String, String> params);

	public void printResponse();

	public JSONObject parseResponseJsonObject();

	public JSONArray parsResponseJsonArray();
}
