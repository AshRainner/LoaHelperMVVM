package com.lostark.loahelper.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.lostark.loahelper.R
import com.lostark.loahelper.database.table.GemItems
import com.lostark.loahelper.database.table.Items
import java.text.DecimalFormat
import kotlin.math.ceil
import kotlin.math.floor


class CalculatorMapFragment(private val mapItemList:ArrayList<Items>, private val lv1Gem: GemItems) : Fragment() {

    private val mapItemsCountList: Map<String,List<Int>> = hashMapOf(
        "파푸니카" to listOf(8,4,4,8,28),
        "베른남부" to listOf(12,8,4,8,40),
        "볼다이크" to listOf(16,8,4,8,48)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.calculator_map_fragment, container, false)

        setData(view)

        return view
    }

    fun setData(view:View){

        val mapNameView = view.findViewById<TextView>(R.id.calculator_map_name)

        val reforge1PriceView = view.findViewById<TextView>(R.id.calculator_map_reforge_1_price)
        val reforge2PriceView = view.findViewById<TextView>(R.id.calculator_map_reforge_2_price)
        val reforge3PriceView = view.findViewById<TextView>(R.id.calculator_map_reforge_3_price)
        val reforge4PriceView = view.findViewById<TextView>(R.id.calculator_map_reforge_4_price)
        val reforge5PriceView = view.findViewById<TextView>(R.id.calculator_map_lv_1_gem_price)

        val reforge1CountView = view.findViewById<TextView>(R.id.calculator_map_reforge_1_count)
        val reforge2CountView = view.findViewById<TextView>(R.id.calculator_map_reforge_2_count)
        val reforge3CountView = view.findViewById<TextView>(R.id.calculator_map_reforge_3_count)
        val reforge4CountView = view.findViewById<TextView>(R.id.calculator_map_reforge_4_count)
        val reforge5CountView = view.findViewById<TextView>(R.id.calculator_map_lv_1_gem_count)


        val reforge1PriceResultView = view.findViewById<TextView>(R.id.calculator_map_reforge_1_price_result)
        val reforge2PriceResultView = view.findViewById<TextView>(R.id.calculator_map_reforge_2_price_result)
        val reforge3PriceResultView = view.findViewById<TextView>(R.id.calculator_map_reforge_3_price_result)
        val reforge4PriceResultView = view.findViewById<TextView>(R.id.calculator_map_reforge_4_price_result)
        val reforge5PriceResultView = view.findViewById<TextView>(R.id.calculator_map_lv_1_gem_price_result)

        val priceList = listOf(reforge1PriceView,reforge2PriceView,reforge3PriceView,reforge4PriceView)
        val resultList = listOf(reforge1PriceResultView,reforge2PriceResultView,reforge3PriceResultView,reforge4PriceResultView)
        val countViewList = listOf(reforge1CountView,reforge2CountView,reforge3CountView,reforge4CountView)

        var countList = mapItemsCountList.get("볼다이크")!!
        var resultPrice:Int=0
        var fee:Int = 0
        mapItemList.forEachIndexed {index, items ->
            priceList.get(index).text = floor(items.yDayAvgPrice).toInt().toString()
            resultList.get(index).text = (priceList.get(index).text.toString().toInt() * countList.get(index)).toString()
            resultPrice+=resultList.get(index).text.toString().toInt()
            fee+=ceil(priceList.get(index).text.toString().toInt()*0.05).toInt()*countList.get(index)
            Log.d("수수료"+index, fee.toString())
        }
        reforge5PriceView.text = lv1Gem.buyPrcie.toString()
        reforge5PriceResultView.text = (lv1Gem.buyPrcie.toString().toInt() * countList.get(4)).toString()

        resultPrice+=reforge5PriceResultView.text.toString().toInt()
        fee+=ceil(reforge5PriceView.text.toString().toInt()*0.05).toInt()*countList.get(4)

        val mapPriceView = view.findViewById<TextView>(R.id.calculator_map_price_result)
        val mapFeeView = view.findViewById<TextView>(R.id.calculator_map_fee)
        val mapBreakPointView = view.findViewById<TextView>(R.id.calculator_map_break_point)
        val mapRecommendView = view.findViewById<TextView>(R.id.calculator_map_recommend_price)
        val mapAllotmentView = view.findViewById<TextView>(R.id.calculator_map_allotment)

        val dec = DecimalFormat("#,###")

        mapPriceView.text = dec.format(resultPrice)
        mapFeeView.text = dec.format(fee)
        val breakPoint = floor(resultPrice*0.95*((30.0-1)/30))
        mapBreakPointView.text = dec.format(breakPoint)
        mapRecommendView.text = dec.format(floor(breakPoint*0.91))
        mapAllotmentView.text = dec.format(floor(breakPoint*0.91/29.0))

        val boldaikuButton = view.findViewById<Button>(R.id.calculator_map_boldaiku_button)
        val berunButton = view.findViewById<Button>(R.id.calculator_map_berun_button)
        val papunikaButton = view.findViewById<Button>(R.id.calculator_map_papunika_button)

        boldaikuButton.setOnClickListener {
            fee=0
            resultPrice=0
            countList = mapItemsCountList.get("볼다이크")!!
            mapNameView.text="볼다이크 지도"
            mapItemList.forEachIndexed {index, items ->
                resultList.get(index).text = (priceList.get(index).text.toString().toInt() * countList.get(index)).toString()
                countViewList.get(index).text = countList.get(index).toString()
                resultPrice+=resultList.get(index).text.toString().toInt()
                fee+=ceil(priceList.get(index).text.toString().toInt()*0.05).toInt()*countList.get(index)

            }
            reforge5PriceResultView.text = (lv1Gem.buyPrcie.toString().toInt() * countList.get(4)).toString()
            resultPrice+=reforge5PriceResultView.text.toString().toInt()
            fee+=ceil(reforge5PriceView.text.toString().toInt()*0.05).toInt()*countList.get(4)
            reforge5CountView.text=countList.get(4).toString()
            mapPriceView.text = dec.format(resultPrice)
            mapFeeView.text = dec.format(fee)
            val breakPoint = floor(resultPrice*0.95*((30.0-1)/30))
            mapBreakPointView.text = dec.format(breakPoint)
            mapRecommendView.text = dec.format(floor(breakPoint*0.91))
            mapAllotmentView.text = dec.format(floor(breakPoint*0.91/29.0))
        }

        berunButton.setOnClickListener {
            fee=0
            resultPrice=0
            countList = mapItemsCountList.get("베른남부")!!
            mapNameView.text="베른남부 지도"
            mapItemList.forEachIndexed {index, items ->
                resultList.get(index).text = (priceList.get(index).text.toString().toInt() * countList.get(index)).toString()
                countViewList.get(index).text = countList.get(index).toString()
                resultPrice+=resultList.get(index).text.toString().toInt()
                fee+=ceil(priceList.get(index).text.toString().toInt()*0.05).toInt()*countList.get(index)

            }
            reforge5PriceResultView.text = (lv1Gem.buyPrcie.toString().toInt() * countList.get(4)).toString()
            resultPrice+=reforge5PriceResultView.text.toString().toInt()
            fee+=ceil(reforge5PriceView.text.toString().toInt()*0.05).toInt()*countList.get(4)
            reforge5CountView.text=countList.get(4).toString()
            mapPriceView.text = dec.format(resultPrice)
            mapFeeView.text = dec.format(fee)
            val breakPoint = floor(resultPrice*0.95*((30.0-1)/30))
            mapBreakPointView.text = dec.format(breakPoint)
            mapRecommendView.text = dec.format(floor(breakPoint*0.91))
            mapAllotmentView.text = dec.format(floor(breakPoint*0.91/29.0))
        }

        papunikaButton.setOnClickListener {
            fee=0
            resultPrice=0
            countList = mapItemsCountList.get("파푸니카")!!
            mapNameView.text="파푸니카 지도"
            mapItemList.forEachIndexed {index, items ->
                resultList.get(index).text = (priceList.get(index).text.toString().toInt() * countList.get(index)).toString()
                countViewList.get(index).text = countList.get(index).toString()
                resultPrice+=resultList.get(index).text.toString().toInt()
                fee+=ceil(priceList.get(index).text.toString().toInt()*0.05).toInt()*countList.get(index)

            }
            reforge5PriceResultView.text = (lv1Gem.buyPrcie.toString().toInt() * countList.get(4)).toString()
            resultPrice+=reforge5PriceResultView.text.toString().toInt()
            fee+=ceil(reforge5PriceView.text.toString().toInt()*0.05).toInt()*countList.get(4)
            reforge5CountView.text=countList.get(4).toString()
            mapPriceView.text = dec.format(resultPrice)
            mapFeeView.text = dec.format(fee)
            val breakPoint = floor(resultPrice*0.95*((30.0-1)/30))
            mapBreakPointView.text = dec.format(breakPoint)
            mapRecommendView.text = dec.format(floor(breakPoint*0.91))
            mapAllotmentView.text = dec.format(floor(breakPoint*0.91/29.0))
        }



    }

}