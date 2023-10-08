package com.lostark.loahelper.module
import com.lostark.loahelper.database.AppDatabase
import com.lostark.loahelper.viewmodel.DataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val diModule = module{
    factory {AppDatabase.getInstance(get())}
    viewModel{DataViewModel(get())}
}