package com.lostark.loahelper.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CharSearchViewPagerAdapter(fragmentActivity:FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    val fragments = mutableListOf<Fragment>()
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments.get(position)

    fun getDesiredHeight(position: Int): Int {
        val layoutParams = fragments[position].view?.layoutParams
        layoutParams?.let {
            println("여기에 들어왔습니다 히히")
            return it.height
        }
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }
    fun addFragment(fragment:Fragment){
        fragments.add(fragment)
    }

}