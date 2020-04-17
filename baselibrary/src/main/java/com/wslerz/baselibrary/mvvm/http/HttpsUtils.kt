package com.wslerz.baselibrary.mvvm.http

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

object HttpsUtils {
    /**
     * trust all the https point
     */
    val sslSocketFactory: SSLSocketFactory
        get() {
            var sslContext: SSLContext? = null
            try {
                sslContext = SSLContext.getInstance("SSL")
                val xTrustArray =
                    arrayOf(TRUST_MANAGER)
                sslContext.init(null, xTrustArray, SecureRandom())
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return sslContext!!.socketFactory
        }

    private const val KEY_STORE_TYPE_BKS = "bks" //证书类型
    private const val KEY_STORE_TYPE_P12 = "PKCS12" //证书类型
    private const val KEY_STORE_PASSWORD = "***" //证书密码（应该是客户端证书密码）
    private const val KEY_STORE_TRUST_PASSWORD = "***" //授信证书密码（应该是服务端证书密码）
    fun getSSLSocketFactory(context: Context): SSLSocketFactory? {
        var trust_input: InputStream? = null
        var client_input: InputStream? = null
        return try {
            trust_input = context.assets.open("ca.bks") //服务器授信证书
            client_input = context.assets.open("outgoing.CertwithKey.pkcs12") //客户端证书
            //            InputStream trust_input = context.getResources().openRawResource(R.raw.client_trust);//服务器授信证书
//            InputStream client_input = context.getResources().openRawResource(R.raw.client);//客户端证书
            val sslContext = SSLContext.getInstance("TLS")
            val trustStore =
                KeyStore.getInstance(KeyStore.getDefaultType())
            trustStore.load(trust_input, KEY_STORE_TRUST_PASSWORD.toCharArray())
            val keyStore =
                KeyStore.getInstance(KEY_STORE_TYPE_P12)
            keyStore.load(client_input, KEY_STORE_PASSWORD.toCharArray())
            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(trustStore)
            val keyManagerFactory =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
            keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray())
            sslContext.init(
                keyManagerFactory.keyManagers,
                trustManagerFactory.trustManagers,
                SecureRandom()
            )
            sslContext.socketFactory
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            try {
                trust_input!!.close()
                client_input!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 主机名验证
     */
    private val DO_NOT_VERIFY =
        HostnameVerifier { hostname, session -> true }
    private val TRUST_MANAGER: X509TrustManager =
        object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<X509Certificate>,
                authType: String
            ) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<X509Certificate>,
                authType: String
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        }

    /**
     * 设置HTTPS认证：默认信任所有证书
     */
    fun setDefaultSslSocketFactory(clientBuilder: OkHttpClient.Builder) {
        clientBuilder.hostnameVerifier(DO_NOT_VERIFY)
        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(
                null,
                arrayOf<TrustManager>(TRUST_MANAGER),
                SecureRandom()
            )
            clientBuilder.sslSocketFactory(sc.socketFactory, TRUST_MANAGER)
        } catch (e: Exception) {
            Log.e("https", "Https认证异常: " + e.message)
        }
    }

    /**
     * 设置HTTPS认证
     */
    fun setTLSSSLSocketFactory(
        clientBuilder: OkHttpClient.Builder,
        ctx: Context,
        httpsCertificate: String
    ) {
        val sslContext = getSSLContext(ctx, httpsCertificate)
        if (sslContext != null) {
            clientBuilder.sslSocketFactory(sslContext.socketFactory, TRUST_MANAGER)
        }
    }

    private fun getSSLContext(
        ctx: Context,
        httpsCertificate: String
    ): SSLContext? {
        var sslContext: SSLContext? = null
        try {
            sslContext = SSLContext.getInstance("TLS")
            val inputStream = ctx.assets.open(httpsCertificate)
            val cerFactory =
                CertificateFactory.getInstance("X.509")
            val cer = cerFactory.generateCertificate(inputStream)
            val keyStore = KeyStore.getInstance("PKCS12")
            keyStore.load(null, null)
            keyStore.setCertificateEntry("trust", cer)
            val keyManagerFactory =
                KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
            keyManagerFactory.init(keyStore, null)
            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            sslContext.init(
                keyManagerFactory.keyManagers,
                trustManagerFactory.trustManagers,
                SecureRandom()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sslContext
    }

    @SuppressLint("TrulyRandom")
    fun createSSLSocketFactory(): SSLSocketFactory? {
        var sSLSocketFactory: SSLSocketFactory? = null
        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(
                null, arrayOf<TrustManager>(TrustAllManager()),
                SecureRandom()
            )
            sSLSocketFactory = sc.socketFactory
        } catch (ignored: Exception) {
        }
        return sSLSocketFactory
    }

    class TrustAllManager : X509TrustManager {
        @SuppressLint("TrustAllX509TrustManager")
        @Throws(CertificateException::class)
        override fun checkClientTrusted(
            chain: Array<X509Certificate>,
            authType: String
        ) {
        }

        @SuppressLint("TrustAllX509TrustManager")
        @Throws(CertificateException::class)
        override fun checkServerTrusted(
            chain: Array<X509Certificate>,
            authType: String
        ) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    }

    class TrustAllHostnameVerifier : HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        override fun verify(
            hostname: String,
            session: SSLSession
        ): Boolean {
            return true
        }
    }
}