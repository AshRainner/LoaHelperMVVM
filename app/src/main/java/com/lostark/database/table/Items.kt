package com.lostark.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="Items")
data class Items(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Id")
    val id: Int,
    @ColumnInfo(name = "Name")
    val name: String,
    @ColumnInfo(name = "IconUrl")
    val iconUrl: String,
    @ColumnInfo(name = "YDayAvgPrice")
    val yDayAvgPrice: Double
)