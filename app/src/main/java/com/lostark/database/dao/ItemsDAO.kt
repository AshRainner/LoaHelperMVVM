package com.lostark.database.dao

import androidx.room.*
import com.lostark.database.table.CraftItems
import com.lostark.database.table.Items
import com.lostark.database.table.LifeItems


@Dao
interface ItemsDAO {

    @Query("SELECT * FROM Items")
    fun getItemList(): List<Items>

    @Query("SELECT * FROM CraftItems")
    fun getCraftItemList(): List<CraftItems>

    @Query("SELECT * FROM LifeItems")
    fun getLifeItemList(): List<LifeItems>

    @Query("SELECT * FROM Items WHERE Name like '%'||:itemName||'%'")
    fun getSelectItemList(itemName: String): List<Items>

    @Query("DELETE FROM Items")
    fun deleteAllItems()

    @Query("DELETE FROM CraftItems")
    fun deleteAllCraftItems()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: Items)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCraftItems(items: CraftItems)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLifeItems(items: LifeItems)

    @Update
    fun updateItems(items: Items)

    @Delete
    fun deleteItems(items: Items)
}