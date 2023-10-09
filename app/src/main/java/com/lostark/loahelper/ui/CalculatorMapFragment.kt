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
import com.lostark.loahelper.databinding.CalculatorMapFragmentBinding
import java.text.DecimalFormat
import kotlin.math.ceil
import kotlin.math.floor


class CalculatorMapFragment(private val mapItemList:ArrayList<Items>, private val lv1Gem: GemItems) : BaseFragment<CalculatorMapFragmentBinding>(CalculatorMapFragmentBinding::inflate) {

    private val mapItemsCountList: Map<String,List<Int>> = hashMapOf(
        "파푸니카" to listOf(8,4,4,8,28),
        "베른남부" to listOf(12,8,4,8,40),
        "볼다이크" to listOf(16,8,4,8,48)
    )
    override fun initView() {
        setData()
    }

    fun setData(){

        binding.run {

            val priceList =
                listOf(calculatorMapReforge1Price, calculatorMapReforge2Price, calculatorMapReforge3Price, calculatorMapReforge4Price)
            val resultList = listOf(
                calculatorMapReforge1PriceResult,
                calculatorMapReforge2PriceResult,
                calculatorMapReforge3PriceResult,
                calculatorMapReforge4PriceResult
            )
            val countViewList =
                listOf(calculatorMapReforge1Count, calculatorMapReforge2Count, calculatorMapReforge3Count, calculatorMapReforge4Count)

            var countList = mapItemsCountList.get("볼다이크")!!
            var resultPrice: Int = 0
            var fee: Int = 0
            mapItemList.forEachIndexed { index, items ->
                priceList.get(index).text = floor(items.yDayAvgPrice).toInt().toString()
                resultList.get(index).text =
                    (priceList.get(index).text.toString().toInt() * countList.get(index)).toString()
                resultPrice += resultList.get(index).text.toString().toInt()
                fee += ceil(
                    priceList.get(index).text.toString().toInt() * 0.05
                ).toInt() * countList.get(index)
                Log.d("수수료" + index, fee.toString())
            }
            calculatorMapLv1GemPrice.text = lv1Gem.buyPrcie.toString()
            calculatorMapLv1GemPriceResult.text =
                (lv1Gem.buyPrcie.toString().toInt() * countList.get(4)).toString()

            resultPrice += calculatorMapLv1GemPriceResult.text.toString().toInt()
            fee += ceil(calculatorMapLv1GemPrice.text.toString().toInt() * 0.05).toInt() * countList.get(4)

            val dec = DecimalFormat("#,###")

            calculatorMapPriceResult.text = dec.format(resultPrice)
            calculatorMapFee.text = dec.format(fee)
            val breakPoint = floor(resultPrice * 0.95 * ((30.0 - 1) / 30))
            calculatorMapBreakPoint.text = dec.format(breakPoint)
            calculatorMapRecommendPrice.text = dec.format(floor(breakPoint * 0.91))
            calculatorMapAllotment.text = dec.format(floor(breakPoint * 0.91 / 29.0))

            calculatorMapBoldaikuButton.setOnClickListener {
                fee = 0
                resultPrice = 0
                countList = mapItemsCountList.get("볼다이크")!!
                calculatorMapName.text = "볼다이크 지도"
                mapItemList.forEachIndexed { index, items ->
                    resultList.get(index).text = (priceList.get(index).text.toString()
                        .toInt() * countList.get(index)).toString()
                    countViewList.get(index).text = countList.get(index).toString()
                    resultPrice += resultList.get(index).text.toString().toInt()
                    fee += ceil(
                        priceList.get(index).text.toString().toInt() * 0.05
                    ).toInt() * countList.get(index)

                }
                calculatorMapLv1GemPriceResult.text =
                    (lv1Gem.buyPrcie.toString().toInt() * countList.get(4)).toString()
                resultPrice += calculatorMapLv1GemPriceResult.text.toString().toInt()
                fee += ceil(
                    calculatorMapLv1GemPrice.text.toString().toInt() * 0.05
                ).toInt() * countList.get(4)
                calculatorMapLv1GemCount.text = countList.get(4).toString()
                calculatorMapPriceResult.text = dec.format(resultPrice)
                calculatorMapFee.text = dec.format(fee)
                val breakPoint = floor(resultPrice * 0.95 * ((30.0 - 1) / 30))
                calculatorMapBreakPoint.text = dec.format(breakPoint)
                calculatorMapRecommendPrice.text = dec.format(floor(breakPoint * 0.91))
                calculatorMapAllotment.text = dec.format(floor(breakPoint * 0.91 / 29.0))
            }

            calculatorMapBerunButton.setOnClickListener {
                fee = 0
                resultPrice = 0
                countList = mapItemsCountList.get("베른남부")!!
                calculatorMapName.text = "베른남부 지도"
                mapItemList.forEachIndexed { index, items ->
                    resultList.get(index).text = (priceList.get(index).text.toString()
                        .toInt() * countList.get(index)).toString()
                    countViewList.get(index).text = countList.get(index).toString()
                    resultPrice += resultList.get(index).text.toString().toInt()
                    fee += ceil(
                        priceList.get(index).text.toString().toInt() * 0.05
                    ).toInt() * countList.get(index)

                }
                calculatorMapLv1GemPriceResult.text =
                    (lv1Gem.buyPrcie.toString().toInt() * countList.get(4)).toString()
                resultPrice += calculatorMapLv1GemPriceResult.text.toString().toInt()
                fee += ceil(
                    calculatorMapLv1GemPrice.text.toString().toInt() * 0.05
                ).toInt() * countList.get(4)
                calculatorMapLv1GemCount.text = countList.get(4).toString()
                calculatorMapPriceResult.text = dec.format(resultPrice)
                calculatorMapFee.text = dec.format(fee)
                val breakPoint = floor(resultPrice * 0.95 * ((30.0 - 1) / 30))
                calculatorMapBreakPoint.text = dec.format(breakPoint)
                calculatorMapRecommendPrice.text = dec.format(floor(breakPoint * 0.91))
                calculatorMapAllotment.text = dec.format(floor(breakPoint * 0.91 / 29.0))
            }

            calculatorMapPapunikaButton.setOnClickListener {
                fee = 0
                resultPrice = 0
                countList = mapItemsCountList.get("파푸니카")!!
                calculatorMapName.text = "파푸니카 지도"
                mapItemList.forEachIndexed { index, items ->
                    resultList.get(index).text = (priceList.get(index).text.toString()
                        .toInt() * countList.get(index)).toString()
                    countViewList.get(index).text = countList.get(index).toString()
                    resultPrice += resultList.get(index).text.toString().toInt()
                    fee += ceil(
                        priceList.get(index).text.toString().toInt() * 0.05
                    ).toInt() * countList.get(index)

                }
                calculatorMapLv1GemPriceResult.text =
                    (lv1Gem.buyPrcie.toString().toInt() * countList.get(4)).toString()
                resultPrice += calculatorMapLv1GemPriceResult.text.toString().toInt()
                fee += ceil(
                    calculatorMapLv1GemPrice.text.toString().toInt() * 0.05
                ).toInt() * countList.get(4)
                calculatorMapLv1GemCount.text = countList.get(4).toString()
                calculatorMapPriceResult.text = dec.format(resultPrice)
                calculatorMapFee.text = dec.format(fee)
                val breakPoint = floor(resultPrice * 0.95 * ((30.0 - 1) / 30))
                calculatorMapBreakPoint.text = dec.format(breakPoint)
                calculatorMapRecommendPrice.text = dec.format(floor(breakPoint * 0.91))
                calculatorMapAllotment.text = dec.format(floor(breakPoint * 0.91 / 29.0))
            }
        }


    }

}