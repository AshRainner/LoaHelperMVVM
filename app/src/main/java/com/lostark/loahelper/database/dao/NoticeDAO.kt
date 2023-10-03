package com.lostark.loahelper.database.dao

import androidx.room.*
import com.lostark.loahelper.database.table.Notice


@Dao
interface NoticeDAO {

    @Query("SELECT * FROM Notice ORDER BY UploadDate DESC")
    fun getNoticeList(): List<Notice>

    @Query("DELETE FROM Notice")
    fun deleteAllEvents()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotice(notice: Notice)

    @Delete
    fun deleteNotice(notice: Notice)
}