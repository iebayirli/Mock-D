package com.iebayirli.mockd.util.error_dialog

import android.app.AlertDialog
import android.content.Context

class ErrorDialog(
    context: Context,
    val errorModel: ErrorModel
) {
    private val alertDialogBuilder = AlertDialog.Builder(context)

    init {
        with(alertDialogBuilder) {
            errorModel.title?.let { setTitle(it) }
            errorModel.message?.let { setMessage(it) }
            setCancelable(errorModel.cancelable)
        }
    }

    fun show() = alertDialogBuilder.show()
}