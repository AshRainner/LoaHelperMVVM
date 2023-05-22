package com.lostark.database.table

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName="Events")
@Parcelize
data class LoaEvents(
    @PrimaryKey
    @ColumnInfo(name = "Link")
    val link: String,
    @ColumnInfo(name = "Title")
    val title: String,
    @ColumnInfo(name = "ThumbnailUrl")
    val thumbnailUrl: String
) : Parcelable
