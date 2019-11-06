package com.stardog.web.reginald;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

import javax.net.ssl.SSLContext;

import com.stardog.license.util.LicenseFlow;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {

	private static CloseableHttpClient HTTP_CLIENT;

	static {
		try {
			HTTP_CLIENT = HttpClients.custom()
			                         .useSystemProperties()
			                         .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContext.getDefault(), new String[] { "TLSv1.1", "TLSv1.2" }, null, new DefaultHostnameVerifier()))
			                         .build();
		}
		catch (NoSuchAlgorithmException ignored) {}
	}

	/**
	 * This function listens at endpoint "/api/HttpTrigger-Java". Two ways to invoke it using "curl" command in bash: 1. curl -d "HTTP Body" {your
	 * host}/api/HttpTrigger-Java&code={your function key} 2. curl "{your host}/api/HttpTrigger-Java?name=HTTP%20Query&code={your function key}" Function Key is not needed when
	 * running locally, it is used to invoke function deployed to Azure. More details: https://aka.ms/functions_authorization_keys
	 */
	@FunctionName("HttpTrigger-Java")
	public HttpResponseMessage run(
		@HttpTrigger(name = "req", methods = { HttpMethod.GET, HttpMethod.POST }, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
		final ExecutionContext context) {
		context.getLogger().info("Java HTTP trigger processed a request.");

		final Config aConfig = new Config();

		try  {
			HttpPost aRequest = new HttpPost(aConfig.getReggieLambdaUrl());
			JSONObject aJSONObject = new JSONObject(request.getBody().orElse("{}"));
			if (aJSONObject.has("email")) {
				aRequest.setEntity(new StringEntity(aJSONObject.toString()));

				HttpResponse aResponse = HTTP_CLIENT.execute(aRequest);
				LicenseFlow aResponseFlow = LicenseFlow.fromJson(EntityUtils.toString(aResponse.getEntity()));
				if (aResponseFlow.hasError()) {
					return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body(aResponseFlow.error()).build();
				}

				return request.createResponseBuilder(HttpStatus.OK).body(Base64.getEncoder().encode(aResponseFlow.license())).build();

			}
		}
		catch (Exception e) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			e.printStackTrace( new PrintStream(baos));
			return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body(baos.toString()).build();
		}

		return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
