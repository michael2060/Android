package com.honda.logi.pjwms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class Fragment_ocr : Fragment() {

    private lateinit var tabpageadapter: Tabpageadapter
    private lateinit var viewpager: ViewPager2
    private lateinit var tablayout: TabLayout
    private lateinit var pages: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ocr, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pages = arrayListOf("first", "second")

        tabpageadapter = Tabpageadapter(this, pages)
        viewpager = view.findViewById<ViewPager2>(R.id.fragment_ocr_viewpager)
        tablayout = view.findViewById(R.id.fragment_ocr_tablayout)
        viewpager.adapter = tabpageadapter

        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            tab.text = position.toString()
        }.attach()
    }

    private class Tabpageadapter(val fragment: Fragment, val pages: ArrayList<String>) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = pages.size


        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> BlankFragment().newInstance("first", "frag")
                else -> BlankFragment().newInstance("second", "frag")
            }
        }

    }


}