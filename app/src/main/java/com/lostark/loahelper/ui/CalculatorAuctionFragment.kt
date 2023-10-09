package com.lostark.loahelper.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CalculatorAuctionFragmentBinding
import java.text.DecimalFormat
import kotlin.math.floor


class CalculatorAuctionFragment() : BaseFragment<CalculatorAuctionFragmentBinding>(CalculatorAuctionFragmentBinding::inflate) {

    override fun initView() {
        setFunction()
    }

    fun setFunction() {
        val dec = DecimalFormat("#,###")

        binding.run {
            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {

                    if (!(calculatorAuctionLowPriceEdit.text.toString() == "") && !(calculatorAuctionPersonEdit.text.toString() == "")) {
                        val low_prcie = calculatorAuctionLowPriceEdit.text.toString().toInt()
                        val person = calculatorAuctionPersonEdit.text.toString().toDouble()
                        val breakPointPrice = floor(low_prcie * 0.95 * ((person - 1) / person))

                        calculatorAuctionBreakPoint.text = dec.format(breakPointPrice)
                        calculatorAuctionRecommendPrice.text = dec.format(floor(breakPointPrice * 0.91))
                    } else {
                        calculatorAuctionBreakPoint.text = ""
                        calculatorAuctionRecommendPrice.text = ""
                    }

                }
            }
            calculatorAuctionLowPriceEdit.addTextChangedListener(textWatcher)
            calculatorAuctionPersonEdit.addTextChangedListener(textWatcher)
        }
    }

}