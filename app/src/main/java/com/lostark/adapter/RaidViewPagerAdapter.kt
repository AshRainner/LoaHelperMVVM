package com.lostark.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.lostark.database.table.LoaEvents
import com.lostark.dto.EventDTO
import com.lostark.loahelper.R

class RaidViewPagerAdapter(val raidDrawableList: MutableList<Int>, val viewPager2: ViewPager2) :
    RecyclerView.Adapter<RaidViewPagerAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val imageView = itemView.findViewById<ImageView>(R.id.cheat_paper_image)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.raid_cheat_paper_image,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(raidDrawableList[position])
        Log.d("ASDF", "onBindViewHolder: ")
    }

    override fun getItemCount(): Int = raidDrawableList.size

}