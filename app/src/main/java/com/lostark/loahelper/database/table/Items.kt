package com.lostark.loahelper.database.table

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName="Items")
@Parcelize
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
) : Parcelable