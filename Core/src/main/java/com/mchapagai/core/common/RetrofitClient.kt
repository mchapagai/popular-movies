package com.mchapagai.core.common

import com.mchapagai.core.BuildConfig
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * [RetrofitClient] a Single class for making the network call for specific endpoint.
 *
 * Object is a singleton by default in Kotlin, ensuring only one instance of the class is
 * created and shared across the application.
 *
 * [OkHttpClient] is used to make configure the underlying HTTP client. An interceptor is added to
 * modify requests and responses. It logs the raw response for debugging purposes.
 * [GsonConverterFactory] is used to convert JSON responses into Java/Kotlin objects.
 *
 * [RetrofitClient] class provides a convenient way to create Retrofit service instances with
 * pre-configured network settings, including API key handling, logging, and timeouts. This
 * promotes code re-usability and consistency in making network calls throughout the application.
 *
 * The [Retrofit] instance is initialized lazily suing the by lazy delegate. This ensures that the
 * [Retrofit] instance is created only when it's first accessed.
 */
object RetrofitClient {
    private const val INTERCEPTOR_TIMEOUT = 15L
    private const val API_KEY = "api_key"

    private val retrofit: Retrofit by lazy {
        val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl: HttpUrl = original.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(API_KEY, BuildConfig.API_KEY)
                    .build()

                val requestBuilder: Request.Builder = original.newBuilder()
                    .url(url)

                val request = requestBuilder.build()
                val response = chain.proceed(request)
                val rawJson = response.body?.string()

                response.newBuilder()
                    .body(
                        (rawJson ?: ""
                                ).toResponseBody(response.body?.contentType())
                    )
                    .build()
            }

        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        httpClient.connectTimeout(INTERCEPTOR_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(logging)

        Retrofit.Builder()
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constants.SERVICE_ENDPOINT)
            .build()
    }

    fun <T> createService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}