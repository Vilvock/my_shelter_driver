package br.com.app5m.appshelterdriver.config

import br.com.app5m.appshelterdriver.controller.webservice.WSConstants
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

    fun retrofit(log_enable: Boolean): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = if (log_enable) OkHttpClient.Builder().connectTimeout(25, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS).readTimeout(25, TimeUnit.SECONDS)
            .addInterceptor(logging).build()
        else
            OkHttpClient.Builder().connectTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS).readTimeout(25, TimeUnit.SECONDS).build()

        return Retrofit.Builder().baseUrl(WSConstants.WS_URL_BASE)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()
    }

}