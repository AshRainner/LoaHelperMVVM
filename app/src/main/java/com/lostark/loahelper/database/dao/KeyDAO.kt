package com.lostark.loahelper.database.dao

import androidx.room.*
import com.lostark.loahelper.database.table.Key


@Dao
interface KeyDAO {
    @Query("SELECT * FROM Key")
    fun getKey(): String

    @Insert
    fun insertKey(key: Key)

    @Delete
    fun deleteKey(key: Key)

    @Query("DELETE FROM Key")
    fun deleteAllKey()
}