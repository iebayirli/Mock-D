package com.iebayirli.mockd.service

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.iebayirli.mockd.data.product.Product
import com.iebayirli.mockd.data.social.Social
import com.iebayirli.mockd.util.retromock.AssetsBodyFactory
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface ProductService {

    @Mock
    @MockResponse(body = "product.json", bodyFactory = AssetsBodyFactory::class)
    @GET("/")
    suspend fun getProductInfo(): Product

    @Mock
    @MockResponse(body = "social.json", bodyFactory = AssetsBodyFactory::class)
    @GET("/")
    suspend fun getSocialInfo(): Social


}