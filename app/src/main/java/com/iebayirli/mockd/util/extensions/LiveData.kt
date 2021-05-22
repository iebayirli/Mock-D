package com.iebayirli.mockd.util.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData


fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, {
        it?.let(observer)
    })
}