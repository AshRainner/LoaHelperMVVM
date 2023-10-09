package com.lostark.loahelper.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.lostark.loahelper.R
import com.lostark.loahelper.callbackinterface.SpinnerChangedCallback
import com.lostark.loahelper.databinding.EngravingAccessoryViewBinding
import com.lostark.searchablespinnerlibrary.SearchableSpinner

class AccessoryView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
)  : BaseLinearLayoutView<EngravingAccessoryViewBinding>(
    context,attrs,defStyleAttr
), SpinnerChangedCallback {
    private lateinit var engravingSpinner: Array<SearchableSpinner>
    private lateinit var engravingPlusMinusSpinner: Array<Spinner>

    private var spinnerChangedCallback: SpinnerChangedCallback? = null

    override fun inflateBinding(inflater: LayoutInflater): EngravingAccessoryViewBinding {
        return EngravingAccessoryViewBinding.inflate(inflater)
    }

    override fun init(context: Context?) {
        binding.run {
            engravingSpinner = arrayOf(
                engravingSpinnerOne,
                engravingSpinnerTwo,
                engravingSpinnerThree
            )
            engravingPlusMinusSpinner = arrayOf(
                engravingSpinnerOnePlus,
                engravingSpinnerTwoPlus,
                engravingSpinnerThreeMinus
            )
            val plusValue =
                arrayOf("0", "+1", "+2", "+3", "+4", "+5", "+6", "+7", "+8", "+9", "+10")
            val plusSpinnerAdapter =
                ArrayAdapter(context!!, R.layout.engraving_spinner_item, plusValue)
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


            engravingSpinner.forEach {
                it.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (view is TextView) {
                            view.setTextColor(Color.BLACK) // 텍스트 색상을 원하는 색으로 변경
                        }
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
                        if (view is TextView) {
                            view.setTextColor(Color.BLACK) // 텍스트 색상을 원하는 색으로 변경
                        }
                        spinnerChangedCallback?.onEngravingSpinnerChanged()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // 선택된 항목이 없을 경우 처리, 필요에 따라 구현합니다.
                    }
                }
            }
        }

    }

    fun getEngravingSpinner() = engravingSpinner

    fun getEngravingPlusMinusSpinner() = engravingPlusMinusSpinner


    @SuppressLint("ResourceAsColor")
    override fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AccessoryViewAttr)
        binding.run {
            accessoryImage.setImageResource(
                typedArray.getResourceId(
                    R.styleable.AccessoryViewAttr_accessoryImageSrc,
                    R.drawable.necklace
                )
            )
            accessoryImage.setBackgroundResource(
                typedArray.getResourceId(
                    R.styleable.AccessoryViewAttr_imageBackground,
                    R.drawable.ancient_background
                )
            )
            typedArray.recycle()
        }
    }

    fun setSpinnerChangedCallback(callback: SpinnerChangedCallback) {
        spinnerChangedCallback = callback
    }

    override fun onEngravingSpinnerChanged() {
        TODO("Not yet implemented")
    }


}