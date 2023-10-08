package com.lostark.loahelper.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.lostark.loahelper.customview.CharSearchCharactersGridLayoutView
import com.lostark.loahelper.R
import com.lostark.loahelper.databinding.CharSearchDetailCharactersFragmentBinding
import com.lostark.loahelper.viewmodel.DataViewModel


class CharactersFragment(private val characters: ArrayList<com.lostark.loahelper.dto.characters.CharactersInfo>) : BaseFragment<CharSearchDetailCharactersFragmentBinding>(R.layout.char_search_detail_characters_fragment) {
    private val dataViewModel: DataViewModel by provideViewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.char_search_detail_characters_fragment, container, false)
        setServerInfo(view)
        return view
    }

    fun setServerInfo(view:View){
        val fragmentMainLayout = view.findViewById<LinearLayout>(R.id.char_search_detail_characters_main_layout)

        val groupedCharacterInfo = characters.groupBy { it.serverName }

        val serverNameList = listOf<String>("루페온","실리안","아만","카마인","카제로스","아브렐슈드","카단","니나브")

        val sortedCharacter = serverNameList.mapNotNull { serverName ->
            groupedCharacterInfo[serverName]
        }.filterNotNull().sortedByDescending { it.size }

        sortedCharacter.forEach {
            val serverLayoutView = CharSearchCharactersGridLayoutView(requireContext())
            serverLayoutView.setViewModel(dataViewModel)
            serverLayoutView.setInfo(it.sortedByDescending { it.itemMaxLevel },(activity as SearchDetailActivity).charInfo.armoryProfile.characterName)
            val marginBottomDp = 10 // 변경하려는 marginBottom 값 (dp)

            val marginBottomPx = (marginBottomDp * resources.displayMetrics.density).toInt()

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            layoutParams.setMargins(0, 0, 0, marginBottomPx)
            serverLayoutView.layoutParams=layoutParams

            fragmentMainLayout.addView(serverLayoutView)
        }


    }

}