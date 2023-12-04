package com.example.newsapp.HiltDI

import com.example.newsapp.RetroFit.APIService
import com.example.newsapp.Utils.NewsAPIHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideAPIService(moshi: Moshi, client: OkHttpClient): APIService =
        Retrofit
            .Builder()
            .baseUrl(NewsAPIHelper.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(APIService::class.java)

    @Provides
    @Singleton
    fun provideOKHttpInterceptor(): HttpLoggingInterceptor {
       return HttpLoggingInterceptor()
           .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOKHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .build()
    }
}
