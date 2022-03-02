package com.app.retrofitcoroutine.networking

import com.app.retrofitcoroutine.networking.ApiService.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object ApiProvider {

     private fun getRetrofit(): Retrofit {
       val logging = HttpLoggingInterceptor()
       logging.level = (HttpLoggingInterceptor.Level.BODY)
       val httpClient = OkHttpClient.Builder()
       httpClient.addInterceptor(logging)
       return Retrofit.Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .client(httpClient.build())
           .build()
   }

   val apiService: ApiService = getRetrofit().create(ApiService::class.java)

    fun <S> createService(serviceClass: Class<S>?): S {
        return getRetrofit().create(serviceClass)
    }
}