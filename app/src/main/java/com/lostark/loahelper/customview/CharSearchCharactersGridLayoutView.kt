package com.lostark.loahelper.customview

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lostark.loahelper.R
import com.lostark.loahelper.database.table.RecentCharInfo
import com.lostark.loahelper.databinding.CharSearchDetailCharactersGridLayoutViewBinding
import com.lostark.loahelper.ui.SearchActivity
import com.lostark.loahelper.ui.SearchDetailActivity
import com.lostark.loahelper.viewmodel.DataViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

class CharSearchCharactersGridLayoutView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseLinearLayoutView<CharSearchDetailCharactersGridLayoutViewBinding>(
    context,
    attrs,
    defStyleAttr
) {
    private lateinit var viewModel: DataViewModel

    override fun init(context: Context?) {
    }

    fun setViewModel(viewModel: DataViewModel) {
        this.viewModel = viewModel
    }


    fun setInfo(
        characters: List<com.lostark.loahelper.dto.characters.CharactersInfo>,
        searchName: String
    ) {
        binding.run {
            charactersGridLayoutServerName.text = characters.get(0).serverName
            charactersGridLayoutHaveChar.text = characters.size.toString()
            characters.forEach {
                val gridItem = CharSearchCharactersGridItemView(context)

                if (it.characterName.equals(searchName))
                    gridItem.selectedChar()
                gridItem.setCharInfo(it)
                val layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setMargins(10,10,10,10)
                }
                gridItem.layoutParams = layoutParams
                val name = it.characterName
                gridItem.setOnClickListener {
                    viewModel.serverNameSearch(
                        name,
                    ) { callbackCharacter ->
                        if (callbackCharacter != null) {

                            viewModel.searchInfo(name) { armory ->
                                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss")
                                val time = LocalDateTime.now().format(formatter)
                                armory?.armoryProfile?.serverName = characters.get(0).serverName
                                val recentInfo = RecentCharInfo(
                                    armory!!.armoryProfile.characterName,
                                    armory!!.armoryProfile.serverName,
                                    armory!!.armoryProfile.itemMaxLevel,
                                    armory!!.armoryProfile.characterClassName,
                                    time
                                )
                                viewModel.insertRecentCharInfo(recentInfo)
                                context.startActivity(
                                    Intent(
                                        Intent(
                                            context,
                                            SearchDetailActivity::class.java
                                        )
                                    ).putExtra("charInfo", armory)
                                        .putExtra("characters", callbackCharacter)
                                )
                                (context as SearchDetailActivity).finish()
                            }
                        }
                    }
                    //val armory = searchActivity.charSearch(name,(context as SearchDetailActivity).db)
                }
                charactersGridLayout.addView(gridItem)
            }
            if (characters.size == 1) {
                val view = View(context)
                val layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                view.layoutParams = layoutParams
                charactersGridLayout.addView(view)
            }
            val layoutParams = charactersGridLayout.layoutParams
            val dp = 10+(95*ceil(characters.size/2.0).toInt())
            layoutParams.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
        }
    }

    override fun getAttrs(attrs: AttributeSet?) {

    }

    override fun inflateBinding(inflater: LayoutInflater): CharSearchDetailCharactersGridLayoutViewBinding {
        return CharSearchDetailCharactersGridLayoutViewBinding.inflate(inflater)
    }
}