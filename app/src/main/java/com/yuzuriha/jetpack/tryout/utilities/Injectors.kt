package com.yuzuriha.jetpack.tryout.utilities

import com.yuzuriha.jetpack.tryout.service.PostService
import com.yuzuriha.jetpack.tryout.viewmodel.AppViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    viewModel { AppViewModel(get()) }
    single { PostService() }
}