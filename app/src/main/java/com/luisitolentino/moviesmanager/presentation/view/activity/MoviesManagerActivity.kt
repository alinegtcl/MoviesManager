package com.luisitolentino.moviesmanager.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.luisitolentino.moviesmanager.R
import com.luisitolentino.moviesmanager.databinding.ActivityMoviesManagerBinding

class MoviesManagerActivity : AppCompatActivity() {

    private val binding: ActivityMoviesManagerBinding by lazy {
        ActivityMoviesManagerBinding.inflate(layoutInflater)
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarMoviesManager)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerMoviesManager) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerMoviesManager)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}