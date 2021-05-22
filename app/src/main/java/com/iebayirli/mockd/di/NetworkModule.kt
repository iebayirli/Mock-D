package com.iebayirli.mockd.di

import android.content.res.AssetManager
import co.infinum.retromock.Retromock
import com.iebayirli.mockd.util.retromock.AssetsBodyFactory
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
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            it.proceed(it.request())
        }
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://TEMP")
            .client(okHttpClient)
            .build()

    @Provides
    fun provideAssetBodyFactory(assetManager: AssetManager): AssetsBodyFactory =
        AssetsBodyFactory(assetManager)

    @Provides
    @Singleton
    fun provideRetromock(retrofit: Retrofit, assetsBodyFactory: AssetsBodyFactory): Retromock =
        Retromock.Builder()
            .retrofit(retrofit)
            .addBodyFactory(assetsBodyFactory)
            .build()
}
