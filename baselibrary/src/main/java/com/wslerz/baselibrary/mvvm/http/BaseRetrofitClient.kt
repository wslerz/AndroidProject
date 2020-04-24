package com.wslerz.baselibrary.mvvm.http

import com.wslerz.baselibrary.BuildConfig
import com.wslerz.baselibrary.mvvm.http.converterFactory.CustomGsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * Created by luyao
 * on 2018/3/13 14:58
 */
abstract class BaseRetrofitClient {

    companion object {
        private const val TIME_OUT = 5L
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

    protected abstract fun handleBuilder(builder: OkHttpClient.Builder)

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
        return logging
    }

    fun <S> getService(serviceClass: Class<S>, baseUrl: String): S {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(CustomGsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
}
