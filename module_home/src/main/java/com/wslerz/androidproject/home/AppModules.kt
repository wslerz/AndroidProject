package com.wslerz.androidproject.home

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @author by lzz
 * @date 2020/4/7
 * @description
 */


val viewModeModule = module {
    viewModel { HomeViewModel(get()) }

}
val repositoryModule = module {
    single { HomeRepository() }
}

val appModules = listOf(
    viewModeModule,
    repositoryModule
)
