package com.lostark.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import com.lostark.dto.armorys.CollectiblePoint
import com.lostark.loahelper.R

class CharSearchCollectionListView : LinearLayout {

    lateinit var collectionListNamePercent:TextView
    lateinit var collectionList:List<CollectiblePoint>
    lateinit var nonHaveCollectionList:List<CollectiblePoint>
    lateinit var collectionListLayout:LinearLayout
    lateinit var collectionSwitch: Switch

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }


    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.char_search_detail_collection_list_view, this, false)
        addView(view)
        collectionListNamePercent = view.findViewById(R.id.collection_list_name_percent)
        collectionListLayout = view.findViewById(R.id.collection_list_layout)
        collectionSwitch = view.findViewById(R.id.collection_list_switch)

    }

    fun setListView(collectiblePoints: List<CollectiblePoint>){
        collectionList=collectiblePoints
        nonHaveCollectionList=collectionList.filter { it.point != it.maxPoint}
        var allViewList = mutableListOf<View>()
        var nonHaveCollectionViewList = mutableListOf<View>()
        collectiblePoints.forEachIndexed { index,item->
            val itemView =  LayoutInflater.from(context).inflate(R.layout.char_search_detail_collection_list_item_view,null)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 0, 0, (5 * resources.displayMetrics.density).toInt())
            itemView.layoutParams = layoutParams
            val collectionNumberView = itemView.findViewById<TextView>(R.id.collection_item_number)
            val collectionNameView = itemView.findViewById<TextView>(R.id.collection_item_name)
            val collectionCheckView = itemView.findViewById<ImageView>(R.id.collection_item_check)
            collectionNumberView.text=(index+1).toString()
            collectionNameView.text=item.pointName
            if(item.maxPoint==item.point) {
                collectionCheckView.visibility = View.VISIBLE
            }
            else{
                nonHaveCollectionViewList.add(itemView)
            }
            allViewList.add(itemView)

        }
        allViewList.forEach {
            collectionListLayout.addView(it)
        }
        collectionSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            collectionListLayout.removeAllViews()
            if(isChecked){
                nonHaveCollectionViewList.forEach {
                    collectionListLayout.addView(it)
                }
            }
            else{
                allViewList.forEach {
                    collectionListLayout.addView(it)
                }
            }

        }
    }

}