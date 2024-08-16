package com.example.geo_weather_lu2

import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager):FragmentPagerAdapter(fm) {
    // Array to hold the resource IDs of tab titles
    private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3
    )
    override fun getItem(position: Int): Fragment {
        //getItem is called to instantiate the fragment for the given page.
        when(position){
            1 -> return DailyForecastsFragment()
        }
        //return a PlaceholderFragment.
        return PlaceholderFragment.newInstance(position + 1);
    }

    override fun getPageTitle(position: Int): CharSequence {

        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        //Show 3 total pages
        return 3
    }
}