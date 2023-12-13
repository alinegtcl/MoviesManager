package com.luisitolentino.moviesmanager.framework.di

import androidx.room.Room
import com.luisitolentino.moviesmanager.data.repository.MovieManagerRepositoryImpl
import com.luisitolentino.moviesmanager.domain.usecase.MovieManagerUseCase
import com.luisitolentino.moviesmanager.framework.datasource.MoviesManagerDatabase
import com.luisitolentino.moviesmanager.presentation.viewmodel.MoviesManagerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    single {
        Room
            .databaseBuilder(
                androidContext(),
                MoviesManagerDatabase::class.java,
                MoviesManagerDatabase.MOVIES_MANAGER_DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<MoviesManagerDatabase>().moviesDao() }
    factory<MovieManagerUseCase> { MovieManagerRepositoryImpl(get()) }
    viewModel { MoviesManagerViewModel(get()) }
}