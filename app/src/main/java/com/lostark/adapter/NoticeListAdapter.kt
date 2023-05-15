package com.lostark.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.lostark.dto.NoticeDTO
import com.lostark.loahelper.R
import retrofit2.Callback

class NoticeListAdapter(val noticeList:MutableList<NoticeDTO>): BaseAdapter() {
    override fun getCount(): Int=noticeList.size

    override fun getItem(position: Int): Any = noticeList.get(position)

    override fun getItemId(position: Int): Long = 0L

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val view = LayoutInflater.from(viewGroup?.context).inflate(R.layout.notices_item,null)
        val title = view.findViewById<TextView>(R.id.notices_title)
        title.text="["+noticeList.get(position).type+"]"+noticeList.get(position).title
        return view
    }
}