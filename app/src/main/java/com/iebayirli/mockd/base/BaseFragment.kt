package com.iebayirli.mockd.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<BINDING : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: VM

    lateinit var decorator: BaseFragmentDecorator<BINDING, VM>

    protected val binding get() = decorator.binder

    abstract fun onReady(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bb = DataBindingUtil.inflate<BINDING>(inflater, layoutId, container, false)
        decorator = BaseFragmentDecorator(this, viewModel, bb)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        decorator.startObservers()
        onReady(savedInstanceState)
    }

}