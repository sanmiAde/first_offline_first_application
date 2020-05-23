package com.sanmidev.firstoffline_firstapplication.di.module

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.sanmidev.firstoffline_firstapplication.data.remote.EmployeeAPI
import com.sanmidev.firstoffline_firstapplication.di.ApplicationScope
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule {
    @Provides
    @ApplicationScope
    fun provideOkHttpChace(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }



    @Provides
    @ApplicationScope
    fun provideOkHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        // TODO check if it logs in release mode
        // TODO check google sign in in release mode


        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .cache(cache)

            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()


    }


    @Provides
    @ApplicationScope
    fun provideRetrofit(
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("http://www.mocky.io/v2")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @ApplicationScope
    fun providesEmployeeWebService( retrofit: Retrofit): EmployeeAPI =
        retrofit.create(EmployeeAPI::class.java)

}