package com.example.mchapagai.service;

import com.example.mchapagai.BuildConfig;
import com.example.mchapagai.common.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl originalHttpUrl = original.url();

                    HttpUrl url = originalHttpUrl.newBuilder()
                            .setQueryParameter(API_KEY, BuildConfig.API_KEY)
                            .build();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("Accept", "application/json")
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
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

/*
    private static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }

    private static OkHttpClient getOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(INTERCEPTOR_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(INTERCEPTOR_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    public static <T> T createService(final Class<T> clazz) {

        OkHttpClient client = getOkHttpClient(getLoggingInterceptor());

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SERVICE_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofit.create(clazz);
    }*/

}
