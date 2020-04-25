package com.example.a27299.cats.module

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal inline val Request.authorized
    get() = if (header("x-api-key") == null)
        newBuilder().addHeader("x-api-key", "bb367e23-308c-4384-98fd-e384f6464ff6").build()
    else this

object AuthorizationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
            chain.proceed(chain.request().authorized)
}