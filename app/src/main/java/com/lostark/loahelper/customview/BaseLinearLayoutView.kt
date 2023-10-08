package com.lostark.loahelper.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseLinearLayoutView<T : ViewBinding> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    protected lateinit var binding: T
    init {
        initBinding()
    }

    private fun initBinding() {
        val inflater = LayoutInflater.from(context)
        binding = inflateBinding(inflater)
        val rootView = binding.root
        addView(rootView)
    }

    abstract fun inflateBinding(inflater: LayoutInflater): T
}