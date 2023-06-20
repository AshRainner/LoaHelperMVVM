package com.lostark.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.lostark.database.table.LoaEvents
import com.lostark.loahelper.R

class EventViewPagerAdapter(val eventList: MutableList<LoaEvents>, val viewPager2: ViewPager2) :
    RecyclerView.Adapter<EventViewPagerAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val imageView = itemView.findViewById<ImageView>(R.id.event_image_view)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_image,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(eventList.get(position).thumbnailUrl).into(holder.imageView)
        holder.imageView.setOnClickListener(View.OnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse(eventList.get(position).link))
            startActivity(it.context,intent,null)

        })
        if (position == eventList.size-1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int = eventList.size

    private val runnable = Runnable {
        eventList.addAll(eventList)
        notifyDataSetChanged()
    }

}