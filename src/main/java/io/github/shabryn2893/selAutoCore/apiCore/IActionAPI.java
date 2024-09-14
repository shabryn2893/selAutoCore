package io.github.shabryn2893.selAutoCore.apiCore;

import java.io.File;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.response.Response;

public interface IActionAPI {

	public Response getRequest(String resourceName);

	public Response postRequest(String resourceName, String payLoad);

	public Response putRequest(String resourceName, String payLoad);

	public Response patchRequest(String resourceName, String payLoad);

	public Response deleteRequest(String resourceName);

	public void setContentType(String contectType);

	public String getContentType();

	public String getHeader(String headerName);

	public void setHeader(String headerName, String headerValue);

	public int getStatusCode();

	public String getErrorMessage();

	public void setParam(String paramKey, String paramValue);

	public void setParams(Map<String, String> params);

	public void printResponse();

	public JSONObject parseResponseJsonObject();

	public JSONArray parsResponseJsonArray();

	public void setMultiPartAttribute(String controlName, File file);
}
