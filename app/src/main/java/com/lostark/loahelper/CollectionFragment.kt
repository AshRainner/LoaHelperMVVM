package com.lostark.loahelper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.lostark.adapter.ValueDataAdapter
import com.lostark.customview.*
import com.lostark.dto.armorys.*
import com.lostark.dto.armorys.tooltips.Tooltip
import com.lostark.dto.armorys.tooltips.ValueData
import kotlin.math.round


class CollectionFragment(private val charInfo: Armories) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.char_search_detail_collection_fragment, container, false)
        setCollectionEquipment(view)
        setCollectionList(view)
        return view
    }

    fun setCollectionEquipment(view: View) {
        val compassView =
            view.findViewById<CharSearchCollectionEquipmentView>(R.id.char_search_detail_collection_compass_view)

        val charmView =
            view.findViewById<CharSearchCollectionEquipmentView>(R.id.char_search_detail_collection_charm_view)

        val insigniaView =
            view.findViewById<CharSearchCollectionEquipmentView>(R.id.char_search_detail_collection_insignia_view)

        compassView.visibility = View.GONE
        charmView.visibility = View.GONE
        insigniaView.visibility = View.GONE

        val insigniaData = charInfo.armoryEquipment.firstOrNull() { it.type == "문장" }

        val charmData = charInfo.armoryEquipment.firstOrNull { it.type == "부적" }

        val compassData = charInfo.armoryEquipment.firstOrNull { it.type == "나침반" }

        insigniaData?.let { armory ->
            toolTipDeserialization(armory)?.let {
                insigniaView.visibility = View.VISIBLE
                insigniaView.setTextImage(armory, it)
                insigniaView.setOnClickListener {
                    (activity as SearchDetailActivity).openDialog(it, "")
                }
            }

        }

        charmData?.let { armory ->
            toolTipDeserialization(armory)?.let {
                charmView.visibility = View.VISIBLE
                charmView.setTextImage(armory, it)
                charmView.setOnClickListener {
                    (activity as SearchDetailActivity).openDialog(it, "")
                }
            }

        }

        compassData?.let { armory ->
            toolTipDeserialization(armory)?.let {
                compassView.visibility = View.VISIBLE
                compassView.setTextImage(armory, it)
                compassView.setOnClickListener {
                    (activity as SearchDetailActivity).openDialog(it, "")
                }
            }

        }


    }

    fun setCollectionList(view: View) {
        val frameLayout =
            view.findViewById<FrameLayout>(R.id.char_search_detail_collection_list_layout)
        val islandListView = CharSearchCollectionListView(context)
        val island = charInfo.collectibles.firstOrNull { it.type == "섬의 마음" }


        val mococoListView = CharSearchCollectionListView(context)
        val mococo = charInfo.collectibles.firstOrNull { it.type == "모코코 씨앗" }


        val greatPicturesListView = CharSearchCollectionListView(context)
        val greatPictures = charInfo.collectibles.firstOrNull { it.type == "위대한 미술품" }


        val giantHeartsListView = CharSearchCollectionListView(context)
        val giantHearts = charInfo.collectibles.firstOrNull { it.type == "거인의 심장" }

        val adventureMedalListView = CharSearchCollectionListView(context)
        val adventureMedal = charInfo.collectibles.firstOrNull { it.type == "이그네아의 징표" }

        val voyageListView = CharSearchCollectionListView(context)
        val voyage = charInfo.collectibles.firstOrNull { it.type == "항해 모험물" }

        val worldTreeListView = CharSearchCollectionListView(context)
        val worldTree = charInfo.collectibles.firstOrNull { it.type == "세계수의 잎" }


        val orpeusStarListView = CharSearchCollectionListView(context)
        val orpeusStar = charInfo.collectibles.firstOrNull { it.type == "오르페우스의 별" }

        val orgelListView = CharSearchCollectionListView(context)
        val orgel = charInfo.collectibles.firstOrNull { it.type == "기억의 오르골" }

        val viewList = listOf(
            islandListView,
            mococoListView,
            greatPicturesListView,
            giantHeartsListView,
            adventureMedalListView,
            voyageListView,
            worldTreeListView,
            orpeusStarListView,
            orgelListView
        )
        val dataList = listOf(
            island,
            mococo,
            greatPictures,
            giantHearts,
            adventureMedal,
            voyage,
            worldTree,
            orpeusStar,
            orgel
        )

        dataList.forEachIndexed { index, item ->
            item?.let { bindingViewList(viewList.get(index), it) }
        }


        val islandItemView =
            view.findViewById<CharSearchCollectionItemView>(R.id.char_search_detail_collection_item_island)
        val giantHeartsItemView =
            view.findViewById<CharSearchCollectionItemView>(R.id.char_search_detail_collection_item_giant)
        val orpeusStarItemView =
            view.findViewById<CharSearchCollectionItemView>(R.id.char_search_detail_collection_item_star)
        val greatPicturesItemView =
            view.findViewById<CharSearchCollectionItemView>(R.id.char_search_detail_collection_item_picture)
        val orgelItemView =
            view.findViewById<CharSearchCollectionItemView>(R.id.char_search_detail_collection_item_orgel)
        val mococoItemView =
            view.findViewById<CharSearchCollectionItemView>(R.id.char_search_detail_collection_item_mococo)
        val adventureMedalItemView =
            view.findViewById<CharSearchCollectionItemView>(R.id.char_search_detail_collection_item_medal)
        val worldTreeItemView =
            view.findViewById<CharSearchCollectionItemView>(R.id.char_search_detail_collection_item_tree)
        val voyageItemView =
            view.findViewById<CharSearchCollectionItemView>(R.id.char_search_detail_collection_item_voyage)

        val itemViewList = listOf(
            islandItemView,
            mococoItemView,
            greatPicturesItemView,
            giantHeartsItemView,
            adventureMedalItemView,
            voyageItemView,
            worldTreeItemView,
            orpeusStarItemView,
            orgelItemView
        )

        itemViewList.forEachIndexed { index, view ->
            dataList.get(index)?.let {
                Glide.with(this).load(it.icon).centerCrop().into(view.backgroundImageView)
                view.progressBar.setMax(it.maxPoint)
                view.progressBar.setProgress(it.point)
                view.collectionHaveText.text = it.point.toString()
                val result = (it.point.toDouble() / it.maxPoint) * 100
                view.percentText.text = (round(result * 10) / 10).toString() + "%"
            }
            view.setOnClickListener {
                frameLayout.removeAllViews()
                frameLayout.addView(viewList.get(index))
                itemViewList.forEach {
                    it.selected(false)
                }
                view.selected(true)
            }
        }

        islandItemView.selected(true)

        frameLayout.addView(islandListView)

    }

    fun bindingViewList(view: CharSearchCollectionListView, data: Collectible) {
        view.collectionListNamePercent.text = data.type + " " + data.point + "/" + data.maxPoint
        view.setListView(data.collectiblePoints)
    }


    fun toolTipDeserialization(vararg items: Any?): Tooltip? {
        val gson = GsonBuilder()
            .registerTypeAdapter(ValueData::class.java, ValueDataAdapter())
            .create()
        val pattern = "<.*?>".toRegex()
        val pattern2 = "<BR>|<br>".toRegex()
        val tooltips = items.mapNotNull { item ->
            when (item) {
                is ArmoryEquipment -> item.tooltip
                is Engraving -> item.tooltip
                is Gem -> item.tooltip
                is Card -> item.tooltip
                is ArmoryAvatar -> item.tooltip
                else -> return null
            }?.replace(pattern2, "\n")?.replace(pattern, "")
        }
        val jsonString = "{\n\"Elements\":\n${tooltips.joinToString(separator = ",\n")}\n}"
        return gson.fromJson(jsonString, Tooltip::class.java)
    }

}