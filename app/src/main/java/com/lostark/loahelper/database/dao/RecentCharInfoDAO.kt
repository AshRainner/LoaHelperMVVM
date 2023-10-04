package com.lostark.loahelper.database.dao

import androidx.room.*
import com.lostark.loahelper.database.table.RecentCharInfo


@Dao
interface RecentCharInfoDAO {

    @Query("SELECT * FROM RecentCharInfo ORDER BY SearchTime DESC")
    fun getRecentCharInfo(): ArrayList<RecentCharInfo>

    @Query("DELETE FROM RecentCharInfo")
    fun deleteUpdateAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharInfo(info: RecentCharInfo)

    @Delete
    fun deleteCharInfo(info: RecentCharInfo)

}