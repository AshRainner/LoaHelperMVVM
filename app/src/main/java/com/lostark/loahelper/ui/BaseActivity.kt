package com.lostark.loahelper.ui

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity<T:ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: T
    inline fun <reified T : ViewModel> provideViewModel() = viewModel<T>()
    protected fun initBinding(inflate: (LayoutInflater) -> T) {
        binding = inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}