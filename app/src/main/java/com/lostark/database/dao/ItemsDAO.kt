package com.lostark.database.dao

import androidx.room.*
import com.lostark.database.table.Items


@Dao
interface ItemsDAO {

    @Query("SELECT * FROM Items")
    fun getItemList(): List<Items>

    @Query("SELECT * FROM Items WHERE Name like '%돌파석%'")
    fun getStoneList(): List<Items>

    @Query("SELECT * FROM Items WHERE Name like '%파괴%'")
    fun getDestructionList(): List<Items>

    @Query("DELETE FROM Items")
    fun deleteAllItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: Items)

    @Update
    fun updateItems(items: Items)

    @Delete
    fun deleteItems(items: Items)
}