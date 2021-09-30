package br.com.app5m.appshelterpassenger.config

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * RetrofitInitializer
 *
 */
class RetrofitInitializer {

    private val wsUrl = "https://app5m.com.br/iusui1872a5a78512rew/sheltermovel/apiv3/user/"
//    private val wsUrl = "https://portalpluraltv.com.br/webservice/user/"

    fun retrofit(log_enable: Boolean): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = if (log_enable) OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).addInterceptor(logging).build()
        else
            OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS).build()

        return Retrofit.Builder().baseUrl(wsUrl)
                .addConverterFactory(GsonConverterFactory.create()).client(client).build()
    }

}