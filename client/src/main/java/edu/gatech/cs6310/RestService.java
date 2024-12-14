package edu.gatech.cs6310;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;

import edu.gatech.cs6310.Exception.ServerResponseException;

public class RestService {
	private String baseUrl;
	private HttpClient httpClient;

	RestService(String baseUrl, HttpClient httpClient){
		this.baseUrl = baseUrl;
		this.httpClient = httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public String get(String path) throws ServerResponseException, Exception {
		HttpRequest httpRequest = HttpRequest.newBuilder()
			.uri(URI.create(baseUrl + path))
			.GET()
			.build();
		HttpResponse<String> httpResponse = httpClient.send(httpRequest,
			HttpResponse.BodyHandlers.ofString());
		return processResponse(httpResponse);
	}

	public String post(String path, JSONObject o) throws ServerResponseException, Exception {
		HttpRequest httpRequest = HttpRequest.newBuilder()
			.uri(URI.create(baseUrl + path))
			.POST(HttpRequest.BodyPublishers.ofString(o.toString()))
			.setHeader("content-type", "application/json")
			.build();
		HttpResponse<String> httpResponse = httpClient.send(httpRequest,
			HttpResponse.BodyHandlers.ofString());
		return processResponse(httpResponse);
	}

	public String delete(String path) throws ServerResponseException, Exception {
		HttpRequest httpRequest = HttpRequest.newBuilder()
			.uri(URI.create(baseUrl + path))
			.DELETE()
			.build();
		HttpResponse<String> httpResponse = httpClient.send(httpRequest,
			HttpResponse.BodyHandlers.ofString());
		return processResponse(httpResponse);
	}

	private String processResponse(HttpResponse<String> httpResponse) throws ServerResponseException {
		String error = "";
		try {
			JSONObject errResp = new JSONObject(httpResponse.body());
			error = errResp.getString("error");
		}
		catch(Exception e){
			error = "";
		}
		if (error != "") {
			//System.out.println("Error: " + error);
			throw new ServerResponseException(error);
		}
		return httpResponse.body();
	}
}
