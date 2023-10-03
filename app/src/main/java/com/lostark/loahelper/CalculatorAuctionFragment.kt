package com.lostark.loahelper

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.DecimalFormat
import kotlin.math.floor


class CalculatorAuctionFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.calculator_auction_fragment, container, false)

        setFunction(view)

        return view
    }

    fun setFunction(view:View){
        val lowPriceEdit = view.findViewById<EditText>(R.id.calculator_auction_low_price_edit)
        val personEdit = view.findViewById<EditText>(R.id.calculator_auction_person_edit)

        val breakPointText = view.findViewById<TextView>(R.id.calculator_auction_break_point)
        val recommendPriceText = view.findViewById<TextView>(R.id.calculator_auction_recommend_price)

        val dec = DecimalFormat("#,###")

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

                if (!(lowPriceEdit.text.toString()=="")&&!(personEdit.text.toString()=="")){
                    val low_prcie=lowPriceEdit.text.toString().toInt()
                    val person = personEdit.text.toString().toDouble()
                    val breakPointPrice = floor(low_prcie*0.95*((person-1)/person))

                    breakPointText.text = dec.format(breakPointPrice)
                    recommendPriceText.text=dec.format(floor(breakPointPrice*0.91))
                }
                else{
                    breakPointText.text=""
                    recommendPriceText.text=""
                }

            }
        }
        lowPriceEdit.addTextChangedListener(textWatcher)
        personEdit.addTextChangedListener(textWatcher)
    }



}