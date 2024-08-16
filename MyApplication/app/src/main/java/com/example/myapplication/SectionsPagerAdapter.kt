package com.example.myapplication

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(fa: FragmentActivity):FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        return ExampleFragment.newInstance(position + 1)
    }

    override fun getItemCount(): Int {
        return 4//Number of tabs
    }
}