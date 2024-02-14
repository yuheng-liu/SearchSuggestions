package com.yuheng.searchsuggestions

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    fun searchInBrowser(query: String) {
        val webPage: Uri = Uri.parse("https://www.google.com/search?q=${query}")
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            Log.d("myDebug", "Activity not found")
        }
    }
}