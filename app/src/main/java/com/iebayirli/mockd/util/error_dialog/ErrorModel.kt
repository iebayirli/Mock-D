package com.iebayirli.mockd.util.error_dialog

data class ErrorModel(
    var title: String? = null,
    var message: String? = null,
    var cancelable: Boolean = true
)
