package com.lostark.loahelper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.lostark.loahelper.customview.*
import com.lostark.loahelper.dto.armorys.*
import com.lostark.loahelper.R
import com.lostark.loahelper.adapter.ValueDataAdapter
import com.lostark.loahelper.databinding.CharSearchDetailCollectionFragmentBinding
import kotlin.math.round


class CollectionFragment(private val charInfo: Armories) : BaseFragment<CharSearchDetailCollectionFragmentBinding>(CharSearchDetailCollectionFragmentBinding::inflate) {

    override fun initView() {
        setCollectionEquipment()
        setCollectionList()
    }

    fun setCollectionEquipment() {

        binding.run {
            charSearchDetailCollectionCompassView.visibility = View.GONE
            charSearchDetailCollectionCharmView.visibility = View.GONE
            charSearchDetailCollectionInsigniaView.visibility = View.GONE

            val insigniaData = charInfo.armoryEquipment.firstOrNull() { it.type == "문장" }

            val charmData = charInfo.armoryEquipment.firstOrNull { it.type == "부적" }

            val compassData = charInfo.armoryEquipment.firstOrNull { it.type == "나침반" }

            insigniaData?.let { armory ->
                toolTipDeserialization(armory)?.let {
                    charSearchDetailCollectionInsigniaView.visibility = View.VISIBLE
                    charSearchDetailCollectionInsigniaView.setTextImage(armory, it)
                    charSearchDetailCollectionInsigniaView.setOnClickListener {
                        (activity as SearchDetailActivity).openDialog(it, "")
                    }
                }

            }

            charmData?.let { armory ->
                toolTipDeserialization(armory)?.let {
                    charSearchDetailCollectionCharmView.visibility = View.VISIBLE
                    charSearchDetailCollectionCharmView.setTextImage(armory, it)
                    charSearchDetailCollectionCharmView.setOnClickListener {
                        (activity as SearchDetailActivity).openDialog(it, "")
                    }
                }

            }

            compassData?.let { armory ->
                toolTipDeserialization(armory)?.let {
                    charSearchDetailCollectionCompassView.visibility = View.VISIBLE
                    charSearchDetailCollectionCompassView.setTextImage(armory, it)
                    charSearchDetailCollectionCompassView.setOnClickListener {
                        (activity as SearchDetailActivity).openDialog(it, "")
                    }
                }
            }
        }


    }

    fun setCollectionList() {
        binding.run {
            val islandListView = CharSearchCollectionListView(requireContext())
            val island = charInfo.collectibles.firstOrNull { it.type == "섬의 마음" }


            val mococoListView = CharSearchCollectionListView(requireContext())
            val mococo = charInfo.collectibles.firstOrNull { it.type == "모코코 씨앗" }


            val greatPicturesListView = CharSearchCollectionListView(requireContext())
            val greatPictures = charInfo.collectibles.firstOrNull { it.type == "위대한 미술품" }


            val giantHeartsListView = CharSearchCollectionListView(requireContext())
            val giantHearts = charInfo.collectibles.firstOrNull { it.type == "거인의 심장" }

            val adventureMedalListView = CharSearchCollectionListView(requireContext())
            val adventureMedal = charInfo.collectibles.firstOrNull { it.type == "이그네아의 징표" }

            val voyageListView = CharSearchCollectionListView(requireContext())
            val voyage = charInfo.collectibles.firstOrNull { it.type == "항해 모험물" }

            val worldTreeListView = CharSearchCollectionListView(requireContext())
            val worldTree = charInfo.collectibles.firstOrNull { it.type == "세계수의 잎" }


            val orpeusStarListView = CharSearchCollectionListView(requireContext())
            val orpeusStar = charInfo.collectibles.firstOrNull { it.type == "오르페우스의 별" }

            val orgelListView = CharSearchCollectionListView(requireContext())
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

            val itemViewList = listOf(
                charSearchDetailCollectionItemIsland,
                charSearchDetailCollectionItemMococo,
                charSearchDetailCollectionItemPicture,
                charSearchDetailCollectionItemGiant,
                charSearchDetailCollectionItemMedal,
                charSearchDetailCollectionItemVoyage,
                charSearchDetailCollectionItemTree,
                charSearchDetailCollectionItemStar,
                charSearchDetailCollectionItemOrgel
            )

            itemViewList.forEachIndexed { index, view ->
                dataList.get(index)?.let {
                    Glide.with(this@CollectionFragment).load(it.icon).centerCrop().into(view.getBackgroundImage())
                    view.getProgressBar().setMax(it.maxPoint)
                    view.getProgressBar().setProgress(it.point)
                    view.getHave().text = it.point.toString()
                    val result = (it.point.toDouble() / it.maxPoint) * 100
                    view.getPercent().text = (round(result * 10) / 10).toString() + "%"
                }
                view.setOnClickListener {
                    charSearchDetailCollectionListLayout.removeAllViews()
                    charSearchDetailCollectionListLayout.addView(viewList.get(index))
                    itemViewList.forEach {
                        it.selected(false)
                    }
                    view.selected(true)
                }
            }

            charSearchDetailCollectionItemIsland.selected(true)

            charSearchDetailCollectionListLayout.addView(islandListView)

        }
    }

    fun bindingViewList(view: CharSearchCollectionListView, data:Collectible) {
        view.getPercent().text = data.type + " " + data.point + "/" + data.maxPoint
        view.setListView(data.collectiblePoints)
    }

    fun toolTipDeserialization(vararg items: Any?): com.lostark.loahelper.dto.armorys.tooltips.Tooltip? {
        val gson = GsonBuilder()
            .registerTypeAdapter(com.lostark.loahelper.dto.armorys.tooltips.ValueData::class.java, ValueDataAdapter())
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
        return gson.fromJson(jsonString, com.lostark.loahelper.dto.armorys.tooltips.Tooltip::class.java)
    }

}