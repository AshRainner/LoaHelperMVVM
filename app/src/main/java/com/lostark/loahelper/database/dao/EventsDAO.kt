package com.lostark.loahelper.database.dao

import androidx.room.*
import com.lostark.loahelper.database.table.LoaEvents


@Dao
interface EventsDAO {

    @Query("SELECT * FROM Events")
    fun getEventList(): List<LoaEvents>

    @Query("DELETE FROM Events")
    fun deleteAllEvents()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvents(events: LoaEvents)

    @Delete
    fun deleteEvents(events: LoaEvents)
}