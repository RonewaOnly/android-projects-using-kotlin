package com.example.geo_weather_lu2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.viewpager2.widget.ViewPager2
import com.example.geo_weather_lu2.ui.theme.Geo_Weather_LU2Theme
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
        private lateinit var sectionPagerAdapter: SectionsPagerAdapter
        private lateinit var viewPager: ViewPager2
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_main)

            // Set up ViewPager2 with adapter
            val adapter = SectionsPagerAdapter(context = ,)
            viewPager.adapter = adapter

            // Link TabLayout with ViewPager2 using TabLayoutMediator
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = "TAB ${position + 1}"
            }.attach()

        }

}



