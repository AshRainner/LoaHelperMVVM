package com.lostark.loahelper.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lostark.loahelper.api.LoaRetrofitObj
import com.lostark.loahelper.database.AppDatabase
import com.lostark.loahelper.database.dao.*
import com.lostark.loahelper.database.table.*
import com.lostark.loahelper.dto.characters.Characters
import com.lostark.loahelper.dto.news.NoticeItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataViewModel(private val db: AppDatabase) : ViewModel() {
    private val ACCEPT = "application/json"
    private val CONTENTTYPE = "application/json"

    private lateinit var itemsDao: ItemsDAO
    private lateinit var eventsDao: EventsDAO
    private lateinit var noticeDao: NoticeDAO
    private lateinit var updateDao: UpdateDAO
    private lateinit var keyDao: KeyDAO
    private lateinit var recentCharInfoDAO: RecentCharInfoDAO

    private val _recentNameList = MutableLiveData<ArrayList<RecentCharInfo>>()

    val recentNameList: LiveData<ArrayList<RecentCharInfo>> get() = _recentNameList


    fun getRecentCharInfoDao(): RecentCharInfoDAO = recentCharInfoDAO
    fun getKey(): String = keyDao.getKey()
    fun getNoticeList(): ArrayList<Notice> = ArrayList(noticeDao.getNoticeList())
    fun getEventList(): ArrayList<LoaEvents> = ArrayList(eventsDao.getEventList())
    fun getRecentCharInfo(): ArrayList<RecentCharInfo> =
        ArrayList(recentCharInfoDAO.getRecentCharInfo())

    fun getStoneList(): ArrayList<Items> =
        ArrayList(itemsDao.getSelectItemList("돌파석").sortedBy { it.id })

    fun getDestructionList(): ArrayList<Items> =
        ArrayList(itemsDao.getSelectItemList("파괴").sortedBy { it.id })

    fun getLv1GemData(): GemItems = itemsDao.getSelectGemItemList("1레벨")
    fun getMapItemList(): ArrayList<Items> {
        val returnList = ArrayList<Items>()
        returnList.addAll(itemsDao.getSelectItemList("태양의").sortedBy { it.id })
        returnList.add(insertMapFragmentsItems(itemsDao, getKey()))
        return returnList
    }

    init {
        db?.run {
            itemsDao = itemsDAO()
            eventsDao = eventsDao()
            noticeDao = noticeDAO()
            updateDao = updateDAO()
            keyDao = keyDao()
            recentCharInfoDAO = recentCharInfoDAO()
        }
    }

    fun insertKey(key: String) {
        keyDao.deleteAllKey()
        keyDao.insertKey(Key(key))
    }

    fun deleteKey() {
        keyDao.deleteAllKey()
    }

    fun insertRecentCharInfo(charInfo: RecentCharInfo) {
        recentCharInfoDAO.insertCharInfo(charInfo)
    }

    fun updateRecentCharInfoList() {
        _recentNameList.postValue(ArrayList(recentCharInfoDAO.getRecentCharInfo()))
    }

    fun serverNameSearch(
        name: String,
        callback: (com.lostark.loahelper.dto.characters.Characters?) -> Unit
    ) {//서치 인포에서 서치를 했는데 유령 계정이라 서버이름이 ""인 경우 수행하는 서치
        var result: com.lostark.loahelper.dto.characters.Characters? = null
        val key = db.keyDao().getKey()
        val call = LoaRetrofitObj.getRetrofitService().getCharacters(ACCEPT, key, name)
        call.enqueue(object : Callback<Characters> {
            override fun onResponse(
                call: Call<Characters>,
                response: Response<com.lostark.loahelper.dto.characters.Characters>
            ) {
                result = response.body()
                callback(result)
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
                result = null
                callback(null)
            }
        })
    }

    fun searchInfo(name: String, callback: (com.lostark.loahelper.dto.armorys.Armories?) -> Unit) {
        var result: com.lostark.loahelper.dto.armorys.Armories? = null
        val call = LoaRetrofitObj.getRetrofitService().getArmories(ACCEPT, getKey(), name)
        call.enqueue(object : Callback<com.lostark.loahelper.dto.armorys.Armories> {
            override fun onResponse(
                call: Call<com.lostark.loahelper.dto.armorys.Armories>,
                response: Response<com.lostark.loahelper.dto.armorys.Armories>
            ) {
                result = response.body()
                callback(result)
            }

            override fun onFailure(
                call: Call<com.lostark.loahelper.dto.armorys.Armories>,
                t: Throwable
            ) {
                result = null
                callback(null)
            }
        })
    }

    fun searchCharacters(
        name: String,
        callback: (ArrayList<com.lostark.loahelper.dto.characters.CharactersInfo>?) -> Unit
    ) {
        var result: com.lostark.loahelper.dto.characters.Characters? = null
        val call = LoaRetrofitObj.getRetrofitService().getCharacters(ACCEPT, getKey(), name)
        call.enqueue(object : Callback<com.lostark.loahelper.dto.characters.Characters> {
            override fun onResponse(
                call: Call<com.lostark.loahelper.dto.characters.Characters>,
                response: Response<com.lostark.loahelper.dto.characters.Characters>
            ) {
                result = response.body()
                callback(result)
            }

            override fun onFailure(
                call: Call<com.lostark.loahelper.dto.characters.Characters>,
                t: Throwable
            ) {
                result = null
                callback(null)
            }
        })
    }

    suspend fun setInit(context: Context): Int {
        var code: Int? = 0

        val result = CoroutineScope(Dispatchers.IO).async {
            db?.run {
                if (getKey() != null) {
                    val apiKeyResult: Int = apiKeyCheck(getKey())
                    when (apiKeyResult) {
                        200 -> {
                            insertNotice()
                            insertStoneItems()
                            insertDestructionStone()
                            insertEvents()
                            insertMapItems()
                            insertMapGemItems()
                        }
                    }
                    apiKeyResult // apiKeyCheck 함수의 결과를 반환
                }
                else
                    0
            }
        }
        code = result.await()!!


        // 비동기 작업이 완료되면 결과를 얻음
        return code
    }

    fun apiKeyCheck(key: String): Int {
        val accept = "application/json"
        if (key == "") {
            return 0
        } else {
            val call = LoaRetrofitObj.getRetrofitService().getNotice(accept, key)
            val response: Response<MutableList<NoticeItem>> = call.execute()
            val errorCode = response.code()
            return errorCode
        }
    }

    fun apiKeyCheckMain(key: String): String {
        val accept = "application/json"
        val call = LoaRetrofitObj.getRetrofitService().getNotice(accept, "bearer " + key)
        val response: Response<MutableList<NoticeItem>> = call.execute()
        val errorCode = response.code()
        var text: String = ""
        when (errorCode) {
            200 -> {
                Log.d("올바른 키입니다.", "errorCode: ")
                keyDao.deleteAllKey()
                keyDao.insertKey(Key("bearer " + key))
                text = "올바른 코드입니다. 변경완료"

            }
            401 -> {
                Log.d("올바르지 않은 키입니다.", "errorCode: ")
                text = "올바르지 않은 키입니다."
            }
            503 -> {
                Log.d("서버가 점검 중입니다.", "errorCode")
                text = "서버가 점검 중입니다."
            }
        }
        return text
    }

    // 공지사항을 가져와 저장하고 반환하는 메서드
    private fun insertNotice() {
        val call = LoaRetrofitObj.getRetrofitService().getNotice(ACCEPT, getKey())
        val noticeList = call.execute().body()!!
        noticeList.forEach {
            var notice = Notice(it.link, it.title, it.date, it.type)
            noticeDao.insertNotice(notice)
        }
    }

    // 지도 계산기에서 필요한 값을 가져와 저장하고 반환하는 메서드
    //여긴 숨 넣 찾는 곳
    private fun insertMapItems() {
        val mapItemsBody = com.lostark.loahelper.dto.markets.MarketsBody(
            "GRADE",
            50000,
            null,
            3,
            null,
            "태양의",
            0,
            "ASC"
        )

        val call = LoaRetrofitObj.getRetrofitService()
            .getItemsInfo(ACCEPT, getKey(), CONTENTTYPE, mapItemsBody)

        val marketsList = call.execute().body()!!

        marketsList.items?.forEach() {
            var item = Items(it.id, it.name, it.iconUrl, it.yDayAvgPrice)
            itemsDao.insertItems(item)
        }
    }

    //명파 주머니는 따로
    private fun insertMapFragmentsItems(itemsDao: ItemsDAO, key: String?): Items {
        val mapItemsBody = com.lostark.loahelper.dto.markets.MarketsBody(
            "GRADE",
            50000,
            null,
            3,
            null,
            "명예의 파편 주머니(대)",
            0,
            "ASC"
        )
        val call =
            LoaRetrofitObj.getRetrofitService().getItemsInfo(ACCEPT, key, CONTENTTYPE, mapItemsBody)
        val marketsList = call.execute().body()!!
        marketsList.items?.forEach() {
            var item = Items(it.id, it.name, it.iconUrl, it.yDayAvgPrice)
            itemsDao.insertItems(item)
        }
        //Log.d("명파 주머니", itemsDao.getSelectItem("명예의 파편 주머니(대)").toString())
        return itemsDao.getSelectItem("명예의 파편 주머니(대)")
    }

    //1레벨 보석은 auction이라 따로 해줘야함
    private fun insertMapGemItems() {
        val searchBody = com.lostark.loahelper.dto.auctions.AuctionsBody(
            "BUY_PRICE",
            210000,
            null,
            3,
            null,
            "1레벨",
            0,
            "ASC"
        )
        //stoneBody == 돌파석 검색용 바디
        val call = LoaRetrofitObj.getRetrofitService()
            .getAuctionItemsInfo(ACCEPT, getKey(), CONTENTTYPE, searchBody)
        val marketsList = call.execute().body()!!
        val gemData = marketsList.items.get(0)
        val gem = GemItems(
            gemData.name,
            gemData.grade,
            gemData.auctionInfo.buyPrice!!,
            gemData.auctionInfo.tradeAllowCount
        )
        itemsDao.deleteAllGemItems() // 이전값 삭제
        itemsDao.insertGemItems(gem)
    }


    private fun insertStoneItems() {
        val stoneBody = com.lostark.loahelper.dto.markets.MarketsBody(
            "GRADE",
            50000,
            null,
            3,
            "희귀",
            " 명예의 돌파석",
            0,
            "ASC"
        )
        //stoneBody == 돌파석 검색용 바디
        val call =
            LoaRetrofitObj.getRetrofitService()
                .getItemsInfo(ACCEPT, getKey(), CONTENTTYPE, stoneBody)
        val marketsList = call.execute().body()!!
        marketsList.items?.forEach {
            var item = Items(it.id, it.name, it.iconUrl, it.yDayAvgPrice)
            itemsDao.insertItems(item)
        }
    }

    private fun insertDestructionStone() {
        var destructionBody = com.lostark.loahelper.dto.markets.MarketsBody(
            "GRADE",
            50000,
            null,
            3,
            null,
            "파괴석 결정",
            0,
            "ASC"
        )
        var call = LoaRetrofitObj.getRetrofitService()
            .getItemsInfo(ACCEPT, getKey(), CONTENTTYPE, destructionBody)
        var marketsList = call.execute().body()!!

        marketsList.items?.forEach {
            var item = Items(it.id, it.name, it.iconUrl, it.yDayAvgPrice)
            itemsDao.insertItems(item)
        }
        destructionBody = com.lostark.loahelper.dto.markets.MarketsBody(
            "GRADE",
            50000,
            null,
            3,
            null,
            "파괴강석",
            0,
            "ASC"
        )
        call = LoaRetrofitObj.getRetrofitService()
            .getItemsInfo(ACCEPT, getKey(), CONTENTTYPE, destructionBody)
        marketsList = call.execute().body()!!

        marketsList.items?.forEach {
            var item = Items(it.id, it.name, it.iconUrl, it.yDayAvgPrice)
            itemsDao.insertItems(item)
        }
    }

    private fun insertEvents() {
        val call = LoaRetrofitObj.getRetrofitService().getEvent(ACCEPT, getKey())
        val eventList = call.execute().body()
        eventList?.forEach {//엘비스 연산 list가 null이 아니면 forEach실행 null일시 뒤에 있는 log.d실행
            eventsDao.insertEvents(LoaEvents(it.link, it.title, it.thumbnail))
        }

    }

    /*private fun compareToDate(time: String, recentTime: String): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val timeDate = sdf.parse(time.split("T").get(0))
        val recentTimeDate = sdf.parse(recentTime.split("T").get(0))
        val cmp = timeDate.compareTo(recentTimeDate)
        return when {
            cmp > 0 -> {
                true
            }
            cmp < 0 -> {
                false
            }
            else -> {
                true
            }
        }
    }

    private fun LoaTimeCheck(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH")
        cal.time = Date()
        if (sdf.format(cal.time).toString().split('T')[1].toInt() < 10)
            cal.add(Calendar.DATE, -1)
        Log.d("LoaTimeCheck", cal.time.toString())
        return sdf.format(cal.time)
    }*/


}
