package com.lostark.loahelper

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import com.lostark.customview.HomeButtonView
import com.lostark.customview.RaidButtonView
import com.lostark.searchablespinnerlibrary.SearchableSpinner

class EngravingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.engraving_activity)

        spinnerSet()
    }

    fun spinnerSet(){
        val engraving_one_spinner = findViewById<SearchableSpinner>(R.id.engraving_spinner_one)
        val engraving_two_spinner = findViewById<SearchableSpinner>(R.id.engraving_spinner_two)

        val engraving = resources.getStringArray(R.array.engraving)
        val spinnerAdapter =
            ArrayAdapter<String>(this, R.layout.engraving_spinner_item, engraving)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        engraving_one_spinner.adapter=spinnerAdapter
        engraving_two_spinner.adapter=spinnerAdapter

        val testEdittext=findViewById<EditText>(R.id.engraving_serialization_text)
        val testButton=findViewById<Button>(R.id.test_button)
        testButton.setOnClickListener{
            if(testEdittext.text.toString().equals("원")) {
                engraving_one_spinner.setSelection(spinnerAdapter.getPosition("원한"))
            }
        }
    }

}