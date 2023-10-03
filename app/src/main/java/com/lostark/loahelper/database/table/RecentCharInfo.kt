package com.lostark.loahelper.database.table

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName="RecentCharInfo")
@Parcelize
data class RecentCharInfo(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "CharName")
    val charName: String,
    @ColumnInfo(name = "Server")
    val serverName: String,
    @ColumnInfo(name = "Level")
    val level: String,
    @ColumnInfo(name = "Class")
    val _class: String,
    @ColumnInfo(name = "SearchTime")
    val searchTime: String
) : Parcelable