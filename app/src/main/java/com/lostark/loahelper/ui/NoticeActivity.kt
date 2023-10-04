package com.lostark.loahelper.ui

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.lostark.loahelper.R
import com.lostark.loahelper.adapter.NoticeListAdapter
import com.lostark.loahelper.database.table.Notice
import com.lostark.loahelper.databinding.MainActivityBinding
import com.lostark.loahelper.databinding.NoticesActivityBinding
import com.lostark.loahelper.viewmodel.DataViewModel

class NoticeActivity : BaseActivity<NoticesActivityBinding>() {
    private val dataViewModel: DataViewModel by provideViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notices_activity)
        initBinding(NoticesActivityBinding::inflate)
        noticeListViewSet()
    }

    private fun noticeListViewSet() {

        binding.run {
            val noticeType = resources.getStringArray(R.array.noticeType)
            val spinnerAdapter =
                ArrayAdapter<String>(
                    this@NoticeActivity,
                    android.R.layout.simple_list_item_1,
                    noticeType
                )
            noticeTypeSpinner.adapter = spinnerAdapter
            noticesList.setOnItemClickListener { adapterView, view, positon, id ->
                val item = adapterView.getItemAtPosition(positon) as Notice
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
                startActivity(intent, null)
            }

            val viewingNoticeList = dataViewModel.getNoticeList()//보여질 공지 목록
            val noticeListAdapter = NoticeListAdapter(viewingNoticeList)
            noticeListAdapter.notifyDataSetChanged()
            noticesList.adapter=noticeListAdapter

            noticeTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p1 is TextView) {
                        p1.setTextColor(Color.BLACK) // 텍스트 색상을 원하는 색으로 변경
                    }
                    search(
                        viewingNoticeList,
                        dataViewModel.getNoticeList(),
                        noticeTypeSpinner,
                        noticeSearch
                    )
                    noticeListAdapter.notifyDataSetChanged()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
            noticeSearch.setOnKeyListener { view, i, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && i == KEYCODE_ENTER) {
                    search(viewingNoticeList,dataViewModel.getNoticeList(),noticeTypeSpinner,noticeSearch)
                    noticeListAdapter.notifyDataSetChanged()
                }
                false
            }
        }
    }

    fun search(viewingList:ArrayList<Notice>, noticeList:ArrayList<Notice>, spinner: Spinner, noticeSearch:EditText){
        viewingList.clear()
        noticeList.forEach {
            if (spinner.selectedItem.toString().equals("전체")) {
                if (it.title.contains(noticeSearch.text))
                    viewingList.add(it)
            }
            else
                if (it.title.contains(noticeSearch.text) && it.type == spinner.selectedItem.toString())
                    viewingList.add(it)
        }

        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)

    }
}
