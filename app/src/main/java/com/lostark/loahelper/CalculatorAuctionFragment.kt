package com.lostark.loahelper

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.findNavController
import com.google.gson.GsonBuilder
import com.lostark.adapter.ValueDataAdapter
import com.lostark.customview.*
import com.lostark.database.table.GemItems
import com.lostark.database.table.Items
import com.lostark.dto.armorys.*
import com.lostark.dto.armorys.tooltips.Tooltip
import com.lostark.dto.armorys.tooltips.ValueData
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.round


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