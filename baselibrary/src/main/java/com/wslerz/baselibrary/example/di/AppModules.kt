package com.wslerz.baselibrary.example.di

import org.koin.dsl.module

/**
 *
 * @author by lzz
 * @date 2020/3/5
 * @description
 */
val viewModelModule = module {
//     viewModel { BaseViewModel() }
}

val repositoryModule = module {
//    single { BaseRepository() }
}

val appModule = listOf(viewModelModule, repositoryModule)