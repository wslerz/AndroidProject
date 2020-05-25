package com.wslerz.baselibrary.mvvm.http

import com.wslerz.baselibrary.mvvm.http.converterFactory.CustomGsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * BaseRetrofitClient
 */
abstract class BaseRetrofitClient {

    companion object {
        private const val TIME_OUT = 60L
    }

    private val client: OkHttpClient
        get() {
            val builder = OkHttpClient.Builder()

            builder
                .addInterceptor(getHttpLoggingInterceptor())
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            handleBuilder(builder)
            HttpsUtils.createSSLSocketFactory()?.let {
                builder.sslSocketFactory(it, HttpsUtils.TrustAllManager())
            }
            return builder.hostnameVerifier(HostnameVerifier { _: String, _: SSLSession ->
                return@HostnameVerifier true
            })
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .followRedirects(true)
                .build()
        }

    protected open fun handleBuilder(builder: OkHttpClient.Builder) {}


    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (isDebug()) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    /**
     * 当前是否处于debug模式  true 显示详细的log 否则不显示log
     */
    protected open fun isDebug() = false

    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(CustomGsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
}
