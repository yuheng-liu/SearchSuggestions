package com.yuheng.searchsuggestions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.yuheng.searchsuggestions.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupListeners()
    }

    private fun setupNavigation() {
        navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setupListeners() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.toolbar.visibility = if (destination.id == R.id.searchSuggestionFragment)
                View.GONE
            else
                View.VISIBLE
        }
    }
}