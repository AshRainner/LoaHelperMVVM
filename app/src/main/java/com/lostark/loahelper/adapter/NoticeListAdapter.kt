package com.lostark.loahelper.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.lostark.loahelper.R
import com.lostark.loahelper.database.table.Notice

class NoticeListAdapter(val noticeList: ArrayList<Notice>): BaseAdapter() {
    override fun getCount(): Int=noticeList.size

    override fun getItem(position: Int): Any = noticeList.get(position)

    override fun getItemId(position: Int): Long = 0L

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val view = LayoutInflater.from(viewGroup?.context).inflate(R.layout.notices_item,null)
        val title = view.findViewById<TextView>(R.id.notices_title)
        title.text="["+noticeList.get(position)?.type+"]"+noticeList.get(position).title
        title.setTextColor(Color.BLACK)
        return view
    }
}