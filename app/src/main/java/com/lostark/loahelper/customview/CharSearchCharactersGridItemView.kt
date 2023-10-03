package com.lostark.loahelper.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.lostark.loahelper.R

class CharSearchCharactersGridItemView : LinearLayout {

    lateinit var charLevelClass:TextView
    lateinit var charName:TextView
    lateinit var charItemLevel:TextView
    lateinit var gridItemCardView:MaterialCardView

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context?) {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.char_search_detail_characters_grid_item_view, this, false)
        addView(view)
        charLevelClass = view.findViewById(R.id.characters_grid_item_char_level_class)
        charName = view.findViewById(R.id.characters_grid_item_char_name)
        charItemLevel = view.findViewById(R.id.characters_grid_item_char_item_level)
        gridItemCardView = view.findViewById(R.id.characters_grid_item_card_view)

    }

    fun selectedChar(){
        gridItemCardView.setStrokeColor(Color.parseColor("#000000"))
    }

    fun setCharInfo(character : com.lostark.loahelper.dto.characters.CharactersInfo) {
        charLevelClass.text = "Lv."+character.characterLevel+" "+character.characterClassName
        charName.text = character.characterName
        charItemLevel.text = character.itemMaxLevel
    }
}