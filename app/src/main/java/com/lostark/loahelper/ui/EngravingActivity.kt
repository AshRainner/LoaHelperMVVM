package com.lostark.loahelper.ui

import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lostark.loahelper.R
import com.lostark.loahelper.callbackinterface.SpinnerChangedCallback
import com.lostark.loahelper.customview.*
import com.lostark.loahelper.databinding.EngravingActivityBinding
import com.lostark.loahelper.databinding.RaidActivityBinding

class EngravingActivity : BaseActivity<EngravingActivityBinding>(), SpinnerChangedCallback {
    private val asDict = mutableMapOf(
        "각" to "각성",
        "결" to "결투의 대가",
        "구" to "구슬 동자",
        "급" to "급소 타격",
        "기" to "기습의 대가||아르데타인의 기술",
        "달" to "달인의 저력",
        "돌" to "돌격대장",
        "바" to "바리케이드",
        "속" to "속전속결",
        "시" to "시선 집중",
        "아" to "아드레날린",
        "안" to "안정된 상태",
        "에" to "에테르 포식자",
        "예" to "예리한 둔기",
        "원" to "원한",
        "위" to "위기 모면",
        "저" to "저주받은 인형",
        "전" to "전문의",
        "정" to "정밀 단도||정기 흡수",
        "중" to "중갑 착용",
        "타" to "타격의 대가",
        "갈" to "갈증",
        "강" to "강화 무기",
        "고" to "고독한 기사",
        "광" to "광기||광전사의 비기",
        "체" to "극의: 체술",
        "교" to "넘치는 교감",
        "달" to "달의 소리",
        "두" to "두 번재 동료",
        "만" to "만개",
        "충" to "멈출 수 없는 충동||충격단련",
        "버" to "버스트",
        "분" to "분노의 망치",
        "사" to "사냥의 시간",
        "상" to "상급 소환사",
        "세" to "세맥타통",
        "심" to "심판자",
        "오" to "오의 강화||오의 난무",
        "억" to "완벽한 억제",
        "이" to "이슬비",
        "일" to "일격필살",
        "잔" to "잔재된 기운",
        "전" to "전투 태세",
        "절" to "절실한 구원||절정||절제",
        "점" to "점화",
        "죽" to "죽음의 습격",
        "중" to "중력 수련",
        "진" to "진호의 유산||진실된 용맹",
        "질" to "질풍노도",
        "처" to "처단자",
        "초" to "초심",
        "축" to "축복의 오라",
        "포" to "포격 강화||포식자",
        "피" to "피스메이커",
        "핸" to "핸드거너",
        "화" to "화력 강화",
        "환" to "환류",
        "황" to "황제의 칙령||황후의 은총",
        "회" to "회귀"
    )
    private lateinit var engravingEditText: EditText
    private lateinit var engravingNameList: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding(EngravingActivityBinding::inflate)
        editTextSet()
        spinnerSet()
    }

    fun editTextSet() {
        engravingEditText = findViewById(R.id.engraving_edit_text)
        engravingEditText.setOnKeyListener { view, i, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_ENTER) {
                if (filterName(engravingEditText.text.toString()))
                    addSelectedEngravingView()
                else
                    Toast.makeText(this, "각인을 똑바로 입력해주세요", Toast.LENGTH_SHORT).show()

            }
            false
        }

    }

    fun spinnerSet() {
        binding.run {
            neckLaceView.setSpinnerChangedCallback(this@EngravingActivity)
            earRingView1.setSpinnerChangedCallback(this@EngravingActivity)
            earRingView2.setSpinnerChangedCallback(this@EngravingActivity)
            ringView1.setSpinnerChangedCallback(this@EngravingActivity)
            ringView2.setSpinnerChangedCallback(this@EngravingActivity)
            abilityView.setSpinnerChangedCallback(this@EngravingActivity)
            bookView1.setSpinnerChangedCallback(this@EngravingActivity)
            bookView2.setSpinnerChangedCallback(this@EngravingActivity)
        }
    }

    override fun onEngravingSpinnerChanged() {
        val selectedEngravigMap = mutableMapOf<String, Int>()
        binding.run {
            neckLaceView.getEngravingSpinner().forEachIndexed() { index, it ->
                val key = it.selectedItem.toString()
                val value =
                    neckLaceView.getEngravingPlusMinusSpinner()[index].selectedItem.toString()
                        .toInt()
                mapPutKeyValue(selectedEngravigMap, key, value)
            }
            earRingView1.getEngravingSpinner().forEachIndexed() { index, it ->
                val key = it.selectedItem.toString()
                val value =
                    earRingView1.getEngravingPlusMinusSpinner()[index].selectedItem.toString()
                        .toInt()
                mapPutKeyValue(selectedEngravigMap, key, value)
            }
            earRingView2.getEngravingSpinner().forEachIndexed() { index, it ->
                val key = it.selectedItem.toString()
                val value =
                    earRingView2.getEngravingPlusMinusSpinner()[index].selectedItem.toString()
                        .toInt()
                mapPutKeyValue(selectedEngravigMap, key, value)
            }
            ringView1.getEngravingSpinner().forEachIndexed() { index, it ->
                val key = it.selectedItem.toString()
                val value =
                    ringView1.getEngravingPlusMinusSpinner()[index].selectedItem.toString().toInt()
                mapPutKeyValue(selectedEngravigMap, key, value)
            }
            ringView2.getEngravingSpinner().forEachIndexed() { index, it ->
                val key = it.selectedItem.toString()
                val value =
                    ringView2.getEngravingPlusMinusSpinner()[index].selectedItem.toString().toInt()
                mapPutKeyValue(selectedEngravigMap, key, value)
            }
            abilityView.getEngravingSpinner().forEachIndexed() { index, it ->
                val key = it.selectedItem.toString()
                val value =
                    abilityView.getEngravingPlusMinusSpinner()[index].selectedItem.toString()
                        .toInt()
                mapPutKeyValue(selectedEngravigMap, key, value)
            }
            mapPutKeyValue(
                selectedEngravigMap,
                bookView1.getEngavingSpinner().selectedItem.toString(),
                bookView1.getEngravingPlusSpinner().selectedItem.toString().toInt()
            )
            mapPutKeyValue(
                selectedEngravigMap,
                bookView2.getEngavingSpinner().selectedItem.toString(),
                bookView2.getEngravingPlusSpinner().selectedItem.toString().toInt()
            )
            changedSelectedEngravingView(selectedEngravigMap)
        }
    }

    private fun mapPutKeyValue(map: MutableMap<String, Int>, key: String, value: Int) {
        if (key == "각인" || key == "감소 각인")
            return
        if (map.containsKey(key)) {
            val oldvalue = map[key]
            map[key] = oldvalue!! + value
        } else {
            map[key] = value
        }
    }


    fun addSelectedEngravingView() {
        binding.run {
            if (selectedEngravingViewLayout.childCount != 0)
                selectedEngravingViewLayout.removeAllViews()
            engravingNameList.forEach {
                val selectedEngravingView = SeletedEngravingView(this@EngravingActivity)
                selectedEngravingView.engravingText.text = it
                selectedEngravingViewLayout.addView(selectedEngravingView)
            }
        }
    }

    fun changedSelectedEngravingView(map: MutableMap<String, Int>) {
        val minusSelectedEngravingViewLayout =
            findViewById<LinearLayout>(R.id.minus_selected_engraving_view_layout)
        minusSelectedEngravingViewLayout.removeAllViews()
        map.forEach {
            if (it.key.contains("감소")) {
                val selectedEngravingView = SeletedEngravingView(this)
                selectedEngravingView.engravingText.text = it.key
                selectedEngravingView.setImage(it.value)
                minusSelectedEngravingViewLayout.addView(selectedEngravingView)
            } else {
                for (i in 0 until binding.selectedEngravingViewLayout.childCount) {
                    val childView =
                        binding.selectedEngravingViewLayout.getChildAt(i) as SeletedEngravingView
                    if (childView.engravingText.text.toString() == it.key) {
                        childView.setImage(it.value)
                    }
                }
            }
        }
    }

    fun filterName(engravingName: String): Boolean {
        engravingNameList = mutableListOf()
        if (engravingName != null) {
            engravingName.forEach {
                if (asDict[it.toString()] != null)
                    engravingNameList.add(asDict[it.toString()]!!)
                else
                    return false

            }
        }
        return true
    }

}