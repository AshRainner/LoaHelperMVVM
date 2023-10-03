package com.lostark.loahelper.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity : AppCompatActivity(), ViewModelStoreOwner {

    private val viewModelStore = ViewModelStore()

    inline fun <reified T : ViewModel> provideViewModel() = viewModel<T>()

    override fun getViewModelStore(): ViewModelStore {
        return viewModelStore
    }

}