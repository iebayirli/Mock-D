package com.iebayirli.mockd.util.extensions

import android.content.Context
import com.iebayirli.mockd.util.error_dialog.ErrorDialog
import com.iebayirli.mockd.util.error_dialog.ErrorModel


fun Context.showErrorDialog(errorModel: ErrorModel) {
    ErrorDialog(this, errorModel).show()
}