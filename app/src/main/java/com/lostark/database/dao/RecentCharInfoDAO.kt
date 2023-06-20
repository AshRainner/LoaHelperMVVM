package com.lostark.database.dao

import androidx.room.*
import com.lostark.database.table.RecentCharInfo
import com.lostark.database.table.UpdateT


@Dao
interface RecentCharInfoDAO {

    @Query("SELECT * FROM RecentCharInfo ORDER BY SearchTime DESC")
    fun getRecentUpdate(): List<RecentCharInfo>

    @Query("DELETE FROM RecentCharInfo")
    fun deleteUpdateAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharInfo(info: RecentCharInfo)

    @Delete
    fun deleteCharInfo(info: RecentCharInfo)

}