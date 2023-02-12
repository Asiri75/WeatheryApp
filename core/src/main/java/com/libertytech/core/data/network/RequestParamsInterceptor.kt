package com.libertytech.core.data.network

import com.libertytech.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestParamsInterceptor : Interceptor {

    /**
     * Add Weather API key in all http requests
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newUrl = originalRequest.url
            .newBuilder()
            .addQueryParameter("appId", BuildConfig.WEATHER_API_KEY)
            .build()

        val newRequest = originalRequest
            .newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}