package com.luisitolentino.moviesmanager.framework.di

import com.luisitolentino.moviesmanager.presentation.viewmodel.MoviesManagerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { MoviesManagerViewModel() }
}