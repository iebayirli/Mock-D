package com.iebayirli.mockd.scene.product_detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.iebayirli.mockd.R
import com.iebayirli.mockd.base.BaseFragment
import com.iebayirli.mockd.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding, ProductDetailViewModel>() {

    override val layoutId: Int = R.layout.fragment_product_detail

    override val viewModel: ProductDetailViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        binding.likeClickListener = viewModel
    }

}