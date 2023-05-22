package com.lostark.database.dao

import androidx.room.*
import com.lostark.database.table.UpdateT


@Dao
interface UpdateDAO {

    @Query("SELECT * FROM UpdateLog")
    fun getRecentUpdate(): List<UpdateT>

    @Insert
    fun insertUpdate(update: UpdateT)

    @Delete
    fun deleteUpdate(update: UpdateT)
}