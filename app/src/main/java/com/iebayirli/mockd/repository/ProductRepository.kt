package com.iebayirli.mockd.repository

import com.iebayirli.mockd.base.BaseRepository
import com.iebayirli.mockd.service.ProductService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productService: ProductService
) : BaseRepository() {


    fun getProductInfo() = safeFlowCall {
        productService.getProductInfo()
    }

    fun getSocialInfo() = safeFlowCall {
        productService.getSocialInfo()
    }

}