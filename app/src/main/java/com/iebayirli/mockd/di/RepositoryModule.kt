package com.iebayirli.mockd.di

import com.iebayirli.mockd.repository.ProductRepository
import com.iebayirli.mockd.service.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideProductRepository(productService: ProductService): ProductRepository =
        ProductRepository(productService)
}