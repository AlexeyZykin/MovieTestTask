package com.alexisdev.movietesttask

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.alexisdev.common.getKoinInstance
import com.alexisdev.common.navigation.NavDirection
import com.alexisdev.common.navigation.NavEffect
import com.alexisdev.common.navigation.NavigationManager
import com.alexisdev.film_catalog.FilmCatalogFragmentDirections
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val navManager: NavigationManager = getKoinInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        navController = this.findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        observeNavigation()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun observeNavigation() {
        lifecycleScope.launch {
            navManager.navEffectFlow.collect { navEffect ->
                when (navEffect) {
                    is NavEffect.NavigateTo -> {
                        handleNavDirection(navEffect.direction, navController)
                    }

                    is NavEffect.NavigateUp -> {
                        navController.navigateUp()
                    }
                }
            }
        }
    }

    private fun handleNavDirection(navDirection: NavDirection, navController: NavController) {
        when (navDirection) {
            is NavDirection.FilmCatalogToFilmDetails -> {
                val action = FilmCatalogFragmentDirections.actionFilmCatalogFragmentToFilmDetailsNavGraph(
                    navDirection.filmId
                )
                navController.navigate(action)
            }
        }
    }
}