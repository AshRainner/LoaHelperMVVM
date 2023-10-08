package com.lostark.loahelper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseFragment<T : ViewBinding>( @LayoutRes val layoutResId: Int) : Fragment() {
    protected lateinit var binding: T
    inline fun <reified T : ViewModel> provideViewModel() = viewModel<T>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId,container, false)
        return binding.root
    }
}