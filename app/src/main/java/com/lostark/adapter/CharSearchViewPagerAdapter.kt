package com.lostark.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.lostark.database.table.LoaEvents
import com.lostark.loahelper.R

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