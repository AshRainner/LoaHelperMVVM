package com.lostark.loahelper.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchDetailCollectionItemViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailCollectionListViewBinding
import com.lostark.loahelper.dto.armorys.CollectiblePoint

class CharSearchCollectionListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailCollectionListViewBinding>(
    context,
    attrs,
    defStyleAttr
) {

    lateinit var collectionList:List<CollectiblePoint>
    lateinit var nonHaveCollectionList:List<CollectiblePoint>


    override fun init(context: Context?) {

    }

    fun setListView(collectiblePoints: List<CollectiblePoint>) {
        binding.run {
            collectionList = collectiblePoints
            nonHaveCollectionList = collectionList.filter { it.point != it.maxPoint }
            var allViewList = mutableListOf<View>()
            var nonHaveCollectionViewList = mutableListOf<View>()
            collectiblePoints.forEachIndexed { index, item ->
                val itemView = LayoutInflater.from(context)
                    .inflate(R.layout.char_search_detail_collection_list_item_view, null)
                val layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(0, 0, 0, (5 * resources.displayMetrics.density).toInt())
                itemView.layoutParams = layoutParams
                val collectionNumberView =
                    itemView.findViewById<TextView>(R.id.collection_item_number)
                val collectionNameView = itemView.findViewById<TextView>(R.id.collection_item_name)
                val collectionCheckView =
                    itemView.findViewById<ImageView>(R.id.collection_item_check)
                collectionNumberView.text = (index + 1).toString()
                collectionNameView.text = item.pointName
                if (item.maxPoint == item.point) {
                    collectionCheckView.visibility = View.VISIBLE
                } else {
                    nonHaveCollectionViewList.add(itemView)
                }
                allViewList.add(itemView)

            }
            allViewList.forEach {
                collectionListLayout.addView(it)
            }
            collectionListSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
                collectionListLayout.removeAllViews()
                if (isChecked) {
                    nonHaveCollectionViewList.forEach {
                        collectionListLayout.addView(it)
                    }
                } else {
                    allViewList.forEach {
                        collectionListLayout.addView(it)
                    }
                }

            }
        }
    }

    fun getPercent()=binding.collectionListNamePercent


    override fun getAttrs(attrs: AttributeSet?) {

    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailCollectionListViewBinding {
        return CharSearchDetailCollectionListViewBinding.inflate(inflater)
    }

}