package com.lostark.customview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.lostark.callbackinterface.SpinnerChangedCallback
import com.lostark.loahelper.R
import com.lostark.searchablespinnerlibrary.SearchableSpinner

class AccessoryView : LinearLayout, SpinnerChangedCallback {
    private lateinit var imageView: ImageView
    private lateinit var engravingSpinner: Array<SearchableSpinner>
    private lateinit var engravingPlusMinusSpinner: Array<Spinner>

    private var spinnerChangedCallback: SpinnerChangedCallback? = null

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
        getAttrs(attrs)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context).inflate(R.layout.engraving_accessory_view, this, false)
        addView(view)
        engravingSpinner = arrayOf(
            findViewById(R.id.engraving_spinner_one),
            findViewById(R.id.engraving_spinner_two),
            findViewById(R.id.engraving_spinner_three)
        )
        engravingPlusMinusSpinner = arrayOf(
            findViewById(R.id.engraving_spinner_one_plus),
            findViewById(R.id.engraving_spinner_two_plus),
            findViewById(R.id.engraving_spinner_three_minus)
        )
        val plusValue = arrayOf("0", "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "+9", "+10")
        val plusSpinnerAdapter = ArrayAdapter(context!!, R.layout.engraving_spinner_item, plusValue)
        plusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        engravingPlusMinusSpinner.forEach { it.adapter = plusSpinnerAdapter }

        val minusValue = arrayOf("0", "-1", "-2", "-3", "-4")
        val minusSpinnerAdapter =
            ArrayAdapter(context!!, R.layout.engraving_spinner_item, minusValue)
        minusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        engravingPlusMinusSpinner[2].adapter = minusSpinnerAdapter

        val engraving = resources.getStringArray(R.array.engraving)
        val engravingSpinnerAdapter =
            ArrayAdapter<String>(context!!, R.layout.engraving_spinner_item, engraving)
        engravingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val minusEngraving = resources.getStringArray(R.array.engraving_minus)
        val minusEngravingSpinnerAdapter =
            ArrayAdapter(context!!, R.layout.engraving_spinner_item, minusEngraving)
        minusEngravingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        engravingSpinner.forEach { it.adapter = engravingSpinnerAdapter }
        engravingSpinner[2].adapter = minusEngravingSpinnerAdapter

        imageView = findViewById(R.id.accessory_image)

        engravingSpinner.forEach {
            it.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    spinnerChangedCallback?.onEngravingSpinnerChanged()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // 선택된 항목이 없을 경우 처리, 필요에 따라 구현합니다.
                }
            }
        }
        engravingPlusMinusSpinner.forEach {
            it.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    spinnerChangedCallback?.onEngravingSpinnerChanged()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // 선택된 항목이 없을 경우 처리, 필요에 따라 구현합니다.
                }
            }
        }

    }

    fun getEngravingSpinner() = engravingSpinner

    fun getEngravingPlusMinusSpinner() = engravingPlusMinusSpinner


    @SuppressLint("ResourceAsColor")
    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AccessoryViewAttr)
        imageView.setImageResource(
            typedArray.getResourceId(
                R.styleable.AccessoryViewAttr_accessoryImageSrc,
                R.drawable.necklace
            )
        )
        imageView.setBackgroundResource(
            typedArray.getResourceId(
                R.styleable.AccessoryViewAttr_imageBackground,
                R.drawable.ancient_background
            )
        )
        typedArray.recycle()
    }

    fun setSpinnerChangedCallback(callback: SpinnerChangedCallback) {
        spinnerChangedCallback = callback
    }

    override fun onEngravingSpinnerChanged() {
        TODO("Not yet implemented")
    }

}