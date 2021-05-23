package com.iebayirli.mockd.util.progress_dialog

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.iebayirli.mockd.R
import com.iebayirli.mockd.databinding.ItemProgressDialogBinding

class ProgressDialog(
    context: Context
) : Dialog(context) {

    private lateinit var binder: ItemProgressDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_progress_dialog,
            null,
            false
        )
        setContentView(binder.root)
        setCancelable(false)

    }
}