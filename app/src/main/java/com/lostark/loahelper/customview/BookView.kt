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
import com.lostark.loahelper.databinding.EngravingBookViewBinding
import com.lostark.searchablespinnerlibrary.SearchableSpinner

class BookView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
)  : BaseLinearLayoutView<EngravingBookViewBinding>(
    context,attrs,defStyleAttr
), SpinnerChangedCallback {
    private var spinnerChangedCallback: SpinnerChangedCallback? = null

    override fun init(context: Context?){
        binding.run {

            val plusValue = arrayOf("0", "+3", "+6", "+9", "+12")
            val plusSpinnerAdapter =
                ArrayAdapter(context!!, android.R.layout.simple_list_item_1, plusValue)

            engravingBookSpinnerPlus.adapter = plusSpinnerAdapter

            val engraving = resources.getStringArray(R.array.engraving)
            val engravingSpinnerAdapter =
                ArrayAdapter<String>(context!!, R.layout.engraving_spinner_item, engraving)
            engravingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            engravingBookSpinner.adapter = engravingSpinnerAdapter

            engravingBookSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

            engravingBookSpinnerPlus.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
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

    fun getEngavingSpinner() = binding.engravingBookSpinner

    fun getEngravingPlusSpinner() = binding.engravingBookSpinnerPlus

    fun setSpinnerChangedCallback(callback: SpinnerChangedCallback) {
        spinnerChangedCallback = callback
    }

    @SuppressLint("ResourceAsColor")
    override fun getAttrs(attrs: AttributeSet?){
    }

    override fun onEngravingSpinnerChanged() {
        TODO("Not yet implemented")
    }

    override fun inflateBinding(inflater: LayoutInflater): EngravingBookViewBinding {
        return EngravingBookViewBinding.inflate(inflater)
    }

}