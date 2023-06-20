package com.lostark.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.lostark.callbackinterface.RecentDeleteButtonClick
import com.lostark.database.table.RecentCharInfo
import com.lostark.loahelper.R

class RecentNameListAdapter(var recentCharInfo: MutableList<RecentCharInfo>): BaseAdapter(),
    RecentDeleteButtonClick {
    private var recentDeleteButtonClickListener : RecentDeleteButtonClick? = null
    override fun getCount(): Int=recentCharInfo.size

    override fun getItem(position: Int): Any = recentCharInfo.get(position)

    override fun getItemId(position: Int): Long = 0L

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val view = LayoutInflater.from(viewGroup?.context).inflate(R.layout.recent_char_name_item,null)
        val serverName = view.findViewById<TextView>(R.id.server_name)
        val charName = view.findViewById<TextView>(R.id.recent_char_name)
        val levelClass = view.findViewById<TextView>(R.id.recent_char_level_class)
        val recentDeleteButton = view.findViewById<ImageView>(R.id.recent_delete_button)
        charName.text= recentCharInfo.get(position).charName
        serverName.text = recentCharInfo.get(position).serverName
        levelClass.text = "Lv. "+recentCharInfo.get(position).level+" "+recentCharInfo.get(position)._class
        recentDeleteButton.setOnClickListener {
            recentDeleteButtonClickListener?.onDeleteClick(position)
        }
        return view
    }

    fun updateList(newList: MutableList<RecentCharInfo>){
        recentCharInfo = newList
        notifyDataSetChanged()
    }

    override fun onDeleteClick(position: Int) {
        TODO("Not yet implemented")
    }
    fun setRecentDeleteButtonClickListener(callback: RecentDeleteButtonClick) {
        recentDeleteButtonClickListener = callback
    }
}