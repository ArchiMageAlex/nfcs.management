package com.sbt.management.ital.service.outlook;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class OutlookServiceBuilder {

	public static OutlookService getOutlookService(final String accessToken, final String userEmail) {
		final Logger LOG = Logger.getLogger(OutlookService.class.getName());

		// Create a request interceptor to add headers that belong on
		// every request
		Interceptor requestInterceptor = new Interceptor() {
			public Response intercept(Interceptor.Chain chain) throws IOException {
				Request original = chain.request();
/*				ResponseBody modifiedBody = null;
				Response orig = chain.proceed(original);
				Response modifiedResponse = null;
				
				if (original.body() != null) {
					MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
					modifiedResponse = orig.newBuilder().body(modifiedBody).build();
					LOG.info("Request body was modified from utf-8");
				}
*/
				Builder builder = original.newBuilder().header("User-Agent", "java-tutorial")
						.header("client-request-id", UUID.randomUUID().toString())
						.header("return-client-request-id", "true")
						.header("Content-Type", "application/json; charset=utf-8")
						.header("Authorization", String.format("Bearer %s", accessToken))
						.method(original.method(), original.body());

				if (userEmail != null && !userEmail.isEmpty()) {
					builder = builder.header("X-AnchorMailbox", userEmail);
				}

				Request request = builder.build();
				return chain.proceed(request);
			}
		};

		// Create a logging interceptor to log request and responses
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(requestInterceptor)
				.addInterceptor(loggingInterceptor).build();

		// Create and configure the Retrofit object
		Retrofit retrofit = new Retrofit.Builder().baseUrl("https://graph.microsoft.com").client(client)
				.addConverterFactory(JacksonConverterFactory.create()).build();

		// Generate the token service
		return retrofit.create(OutlookService.class);
	}
}