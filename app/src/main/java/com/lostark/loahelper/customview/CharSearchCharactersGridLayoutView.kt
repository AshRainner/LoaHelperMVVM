package com.lostark.loahelper.customview

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.lostark.loahelper.R
import com.lostark.loahelper.ui.SearchActivity
import com.lostark.loahelper.ui.SearchDetailActivity

class CharSearchCharactersGridLayoutView : LinearLayout {

    lateinit var layoutServerName:TextView
    lateinit var haveCharInt:TextView
    lateinit var gridLayout:GridLayout

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.char_search_detail_characters_grid_layout_view, this, false)
        addView(view)
        layoutServerName = view.findViewById(R.id.characters_grid_layout_server_name)
        haveCharInt = view.findViewById(R.id.characters_grid_layout_have_char)
        gridLayout = view.findViewById(R.id.characters_grid_layout)
    }


    fun setInfo(characters: List<com.lostark.loahelper.dto.characters.CharactersInfo>, searchName:String) {
        layoutServerName.text = characters.get(0).serverName
        haveCharInt.text = characters.size.toString()

        characters.forEach {
            val gridItem = CharSearchCharactersGridItemView(context)

            if(it.characterName.equals(searchName))
                gridItem.selectedChar()

            gridItem.setCharInfo(it)
            val layoutParams = GridLayout.LayoutParams().apply {
                width=0
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED,1f)
            }
            gridItem.layoutParams = layoutParams
            val name = it.characterName
            gridItem.setOnClickListener {
                val searchActivity = SearchActivity()
                searchActivity.getSearchCharacters(name,(context as SearchDetailActivity).db){ callbackCharacter->
                    if(callbackCharacter!=null){
                        searchActivity.getSearchInfo(name){armory->
                            context.startActivity(
                                Intent(Intent(context, SearchDetailActivity::class.java)).putExtra("charInfo", armory).putExtra("characters", callbackCharacter)
                            )
                            (context as SearchDetailActivity).finish()
                        }
                    }
                }
                //val armory = searchActivity.charSearch(name,(context as SearchDetailActivity).db)
            }

            gridLayout.addView(gridItem)
        }
        if (characters.size==1){
            val view = View(context)
            val layoutParams = GridLayout.LayoutParams().apply {
                width = 0
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED,1f)
            }
            view.layoutParams=layoutParams
            gridLayout.addView(view)
        }
    }

}