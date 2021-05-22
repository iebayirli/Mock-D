package com.iebayirli.mockd.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.iebayirli.mockd.BR
import com.iebayirli.mockd.util.extensions.observeNonNull
import com.iebayirli.mockd.util.progress_dialog.ProgressDialog

abstract class BaseActivity<BINDING : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(),
    BaseView {

    @get:LayoutRes
    abstract val layoutId: Int

    abstract val viewModel: VM

    lateinit var binding: BINDING

    abstract fun onReady(savedInstanceState: Bundle?)

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.setVariable(BR.viewModel, this.viewModel)
        binding.lifecycleOwner = this

        progressDialog = ProgressDialog(this)

        observeProgressState()
        onReady(savedInstanceState)
    }

    private fun observeProgressState() {
        viewModel.progressDialog.observeNonNull(this) {
            it.getContentIfNotHandled()?.let { progressState ->
                if (progressState) showProgressDialog() else dismissProgressDialog()
            }
        }
    }

    override fun showProgressDialog() {
        if (!progressDialog.isShowing)
            progressDialog.show()
    }

    override fun dismissProgressDialog() {
        if (progressDialog.isShowing)
            progressDialog.dismiss()
    }
}