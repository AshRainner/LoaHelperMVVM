package com.lostark.loahelper

import android.graphics.Color
import android.os.Bundle
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
import com.lostark.dto.armorys.*
import com.lostark.dto.armorys.tooltips.Tooltip
import com.lostark.dto.armorys.tooltips.ValueData
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.round


class CalculatorMapFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.calculator_map_fragment, container, false)

        return view
    }

}