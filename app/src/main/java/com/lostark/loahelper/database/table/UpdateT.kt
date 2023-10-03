package com.lostark.loahelper.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="UpdateLog")
data class UpdateT(
    @PrimaryKey
    @ColumnInfo(name = "RecentUpdate")
    val recentUpdate: String

)
