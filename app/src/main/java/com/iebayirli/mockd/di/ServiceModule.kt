package com.iebayirli.mockd.di

import co.infinum.retromock.Retromock
import com.iebayirli.mockd.service.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun provideProductService(retromock: Retromock): ProductService =
        retromock.create(ProductService::class.java)
}