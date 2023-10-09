package com.lostark.loahelper.customview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchDetailCharactersGridItemViewBinding
import com.lostark.loahelper.databinding.CharSearchDetailCharactersGridLayoutViewBinding

class CharSearchCharactersGridItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailCharactersGridItemViewBinding>(
    context,
    attrs,
    defStyleAttr
) {



    override fun init(context: Context?) {

    }

    fun selectedChar(){
        binding.charactersGridItemCardView.setStrokeColor(Color.parseColor("#000000"))
    }

    fun setCharInfo(character : com.lostark.loahelper.dto.characters.CharactersInfo) {
        binding.charactersGridItemCharLevelClass.text = "Lv."+character.characterLevel+" "+character.characterClassName
        binding.charactersGridItemCharName.text = character.characterName
        binding.charactersGridItemCharItemLevel.text = character.itemMaxLevel
    }

    override fun getAttrs(attrs: AttributeSet?) {

    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailCharactersGridItemViewBinding {
        return CharSearchDetailCharactersGridItemViewBinding.inflate(inflater)
    }
}