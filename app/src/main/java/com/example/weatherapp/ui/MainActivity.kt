package com.example.weatherapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.ui.history.WeatherHistoryFragment
import com.example.weatherapp.ui.search.WeatherSearchFragment
import com.example.weatherapp.ui.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeFragment(WeatherSearchFragment())
        setBottomNavigation()

    }

    private fun setBottomNavigation(){
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.miSettings -> changeFragment(SettingsFragment())
                R.id.miHistory -> changeFragment(WeatherHistoryFragment())
                R.id.miSearch -> changeFragment(WeatherSearchFragment())
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun changeFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(binding.flFragment.id,fragment)
            .commit()
    }

}