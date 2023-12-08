package com.luisitolentino.moviesmanager.presentation.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luisitolentino.moviesmanager.R
import com.luisitolentino.moviesmanager.databinding.ActivityMoviesManagerBinding

class MoviesManagerActivity : AppCompatActivity() {

    private val binding: ActivityMoviesManagerBinding by lazy {
        ActivityMoviesManagerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}