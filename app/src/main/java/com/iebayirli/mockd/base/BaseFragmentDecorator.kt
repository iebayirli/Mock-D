package com.iebayirli.mockd.base

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.iebayirli.mockd.BR
import com.iebayirli.mockd.navigation.NavigationCommand
import com.iebayirli.mockd.util.extensions.observeNonNull
import com.iebayirli.mockd.util.extensions.showErrorDialog

class BaseFragmentDecorator<BINDING : ViewDataBinding, VM : BaseViewModel>(
    private val fragment: Fragment,
    private val viewModel: VM,
    val binder: BINDING
) {

    init {
        with(binder) {
            setVariable(BR.viewModel, viewModel)
            lifecycleOwner = fragment.viewLifecycleOwner
        }
    }

    fun startObservers() {
        observeNavigation()
        observeProgressDialogState()
        observeErrorState()
    }

    private fun observeNavigation() {
        viewModel.navigation.observeNonNull(fragment.viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { command ->
                handleNavigation(command)
            }
        }
    }

    private fun handleNavigation(navigationCommand: NavigationCommand) {
        when (navigationCommand) {
            is NavigationCommand.ToDirection -> fragment.findNavController()
                .navigate(navigationCommand.navDirections)
            is NavigationCommand.Back -> fragment.findNavController().navigateUp()
        }
    }

    private fun observeProgressDialogState() {
        viewModel.progressDialog.observeNonNull(fragment.viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { state ->
                if (state) (fragment.activity as BaseActivity<*, *>).showProgressDialog() else
                    (fragment.activity as BaseActivity<*, *>).dismissProgressDialog()
            }
        }
    }

    private fun observeErrorState() {
        viewModel.errorDialog.observeNonNull(fragment.viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { errorModel ->
                fragment.context?.showErrorDialog(errorModel)
            }
        }
    }
}