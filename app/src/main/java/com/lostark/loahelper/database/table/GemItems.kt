package com.lostark.loahelper.database.table

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName="GemItems")
@Parcelize
data class GemItems(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Name")
    val name: String,
    @ColumnInfo(name = "Grade")
    val grade: String,
    @ColumnInfo(name = "BuyPrice")
    val buyPrcie: Int,
    @ColumnInfo(name = "TradeAllowCount")
    val tradeAllowCount:Int
) : Parcelable