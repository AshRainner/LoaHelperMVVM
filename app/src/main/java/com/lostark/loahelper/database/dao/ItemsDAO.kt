package com.lostark.loahelper.database.dao

import androidx.room.*
import com.lostark.loahelper.database.table.CraftItems
import com.lostark.loahelper.database.table.GemItems
import com.lostark.loahelper.database.table.Items
import com.lostark.loahelper.database.table.LifeItems


@Dao
interface ItemsDAO {

    @Query("SELECT * FROM Items")
    fun getItemList(): List<Items>

    @Query("SELECT * FROM GemItems")
    fun getGemItemList(): List<GemItems>

    @Query("SELECT * FROM CraftItems")
    fun getCraftItemList(): List<CraftItems>

    @Query("SELECT * FROM LifeItems")
    fun getLifeItemList(): List<LifeItems>

    @Query("SELECT * FROM Items WHERE Name like '%'||:itemName||'%'")
    fun getSelectItemList(itemName: String): List<Items>

    @Query("SELECT * FROM Items WHERE Name like :itemName")
    fun getSelectItem(itemName: String): Items

    @Query("SELECT * FROM GemItems WHERE Name like '%'||:itemName||'%'")
    fun getSelectGemItemList(itemName: String): GemItems

    @Query("DELETE FROM Items")
    fun deleteAllItems()

    @Query("DELETE FROM CraftItems")
    fun deleteAllCraftItems()

    @Query("DELETE FROM GemItems")
    fun deleteAllGemItems()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItems(items: Items)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCraftItems(items: CraftItems)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLifeItems(items: LifeItems)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGemItems(items: GemItems)

    @Update
    fun updateItems(items: Items)

    @Delete
    fun deleteItems(items: Items)
}