package com.lostark.loahelper.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.lostark.loahelper.R
import com.lostark.loahelper.callbackinterface.RecentDeleteButtonClick
import com.lostark.loahelper.database.table.RecentCharInfo

class RecentNameListAdapter(var recentCharInfo: MutableList<RecentCharInfo>): BaseAdapter(),
    RecentDeleteButtonClick {
    private var recentDeleteButtonClickListener : RecentDeleteButtonClick? = null
    override fun getCount(): Int=recentCharInfo.size

    override fun getItem(position: Int): Any = recentCharInfo.get(position)

    override fun getItemId(position: Int): Long = 0L

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val view = LayoutInflater.from(viewGroup?.context).inflate(R.layout.char_search_recent_char_name_item,null)
        val serverName = view.findViewById<TextView>(R.id.server_name)
        val charName = view.findViewById<TextView>(R.id.recent_char_name)
        val levelClass = view.findViewById<TextView>(R.id.recent_char_level_class)
        val recentDeleteButton = view.findViewById<ImageView>(R.id.recent_delete_button)
        charName.text= recentCharInfo.get(position).charName
        charName.setTextColor(Color.BLACK)
        serverName.text = recentCharInfo.get(position).serverName
        serverName.setTextColor(Color.parseColor("#828282"))
        levelClass.text = "Lv. "+recentCharInfo.get(position).level+" "+recentCharInfo.get(position)._class
        levelClass.setTextColor(Color.parseColor("#828282"))
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