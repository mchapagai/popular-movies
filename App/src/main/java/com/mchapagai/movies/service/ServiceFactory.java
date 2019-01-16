package com.mchapagai.movies.service;

import android.util.Log;

import com.mchapagai.movies.BuildConfig;
import com.mchapagai.movies.common.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Manorath Chapagai
 * Singleton class for making the newtwork call for specifit URL (requests) and getting responses
 */

public class ServiceFactory {

    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static final long INTERCEPTOR_TIMEOUT = 15;
    private static final String API_KEY = "api_key";

    /**
     * Creates a service using the base url
     *
     * @param clazz The class of the service type we want to instantiate
     * @param <T>   The the service type
     * @return An instance of the service
     */

    public static <T> T createService(final Class<T> clazz) {
        if (retrofit == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

            httpClient.addInterceptor(chain -> {
                Request original = chain.request();

                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .setQueryParameter(API_KEY, BuildConfig.API_KEY)
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();

                Response response = chain.proceed(request);
                String rawJson = null;
                if (response.body() != null) {
                    rawJson = response.body().string();
                }

                try {
                    Object object = new JSONTokener(rawJson).nextValue();
                    String jsonLog = object instanceof JSONObject
                            ? ((JSONObject) object).toString(4)
                            : ((JSONArray) object).toString(4);
                    Log.d("jsonLog", jsonLog);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Re-create the response before returning it because body can be read only once
                return response.newBuilder()
                        .body(ResponseBody.create(
                                response.body() != null ? response.body().contentType() : null,
                                rawJson)).build();
            });

            if (BuildConfig.DEBUG) {
                logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                logging.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            httpClient.connectTimeout(INTERCEPTOR_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(logging);

            OkHttpClient okHttpClient = httpClient.build();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constants.SERVICE_ENDPOINT);

            retrofit = builder.build();
        }
        return retrofit.create(clazz);
    }

}
