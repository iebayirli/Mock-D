package com.iebayirli.mockd.scene.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iebayirli.mockd.base.BaseViewModel
import com.iebayirli.mockd.data.product.Product
import com.iebayirli.mockd.repository.ProductRepository
import com.iebayirli.mockd.service.ProductService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : BaseViewModel() {

    init {
        viewModelScope.launch {
            apiCallWithFlow(
                request = productRepository.getProductInfo(),
                loading = { true },
                collect = {

                },
                catch = {
                    ""
                })
        }
    }


}