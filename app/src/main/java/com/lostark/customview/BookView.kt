package com.lostark.customview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.lostark.callbackinterface.SpinnerChangedCallback
import com.lostark.loahelper.R
import com.lostark.searchablespinnerlibrary.SearchableSpinner

class BookView : LinearLayout, SpinnerChangedCallback{
    private lateinit var imageView : ImageView
    private lateinit var engravingSpinner:SearchableSpinner
    private lateinit var engravingPlusSpinner:Spinner

    private var spinnerChangedCallback: SpinnerChangedCallback? = null

    constructor(context: Context?) : super(context){
        init(context)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context,attrs){
        init(context)
        getAttrs(attrs)
    }

    private fun init(context: Context?){
        val view = LayoutInflater.from(context).inflate(R.layout.engraving_book_view,this,false)
        addView(view)

        engravingSpinner = findViewById(R.id.engraving_book_spinner)
        engravingPlusSpinner = findViewById(R.id.engraving_book_spinner_plus)

        val plusValue = arrayOf("0","+3", "+6", "+9", "+12")
        val plusSpinnerAdapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, plusValue)

        engravingPlusSpinner.adapter=plusSpinnerAdapter

        val engraving = resources.getStringArray(R.array.engraving)
        val engravingSpinnerAdapter =
            ArrayAdapter<String>(context!!, R.layout.engraving_spinner_item, engraving)
        engravingSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        engravingSpinner.adapter = engravingSpinnerAdapter

        imageView = findViewById(R.id.book_image)

        engravingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        engravingPlusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

    fun getEngavingSpinner() = engravingSpinner

    fun getEngravingPlusSpinner() = engravingPlusSpinner

    fun setSpinnerChangedCallback(callback: SpinnerChangedCallback) {
        spinnerChangedCallback = callback
    }

    @SuppressLint("ResourceAsColor")
    private fun getAttrs(attrs: AttributeSet?){
    }

    override fun onEngravingSpinnerChanged() {
        TODO("Not yet implemented")
    }

}