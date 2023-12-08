package com.luisitolentino.moviesmanager.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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