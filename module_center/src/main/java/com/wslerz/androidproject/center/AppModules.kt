package com.wslerz.androidproject.center

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 *
 * @author by lzz
 * @date 2020/4/7
 * @description
 */


val viewModeModule = module {
    viewModel { CenterViewModel(get()) }

}
val repositoryModule = module {
    single { CenterRepository() }
}

val appModules = listOf(
    viewModeModule,
    repositoryModule
)
