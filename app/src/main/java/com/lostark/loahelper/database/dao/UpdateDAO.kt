package com.lostark.loahelper.database.dao

import androidx.room.*
import com.lostark.loahelper.database.table.UpdateT


@Dao
interface UpdateDAO {

    @Query("SELECT * FROM UpdateLog")
    fun getRecentUpdate(): List<UpdateT>

    @Query("DELETE FROM UpdateLog")
    fun deleteUpdateAll()

    @Insert
    fun insertUpdate(update: UpdateT)

    @Delete
    fun deleteUpdate(update: UpdateT)
}