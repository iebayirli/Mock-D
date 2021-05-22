package com.iebayirli.mockd.scene.main

import android.os.Bundle
import androidx.activity.viewModels
import com.iebayirli.mockd.R
import com.iebayirli.mockd.base.BaseActivity
import com.iebayirli.mockd.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutId: Int = R.layout.activity_main

    override val viewModel: MainViewModel by viewModels()

    override fun onReady(savedInstanceState: Bundle?) {
        // on Ready
    }

}