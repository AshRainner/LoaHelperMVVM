package com.lostark.loahelper.database.table

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName="Notice")
@Parcelize
data class Notice(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Link")
    val link: String,
    @ColumnInfo(name = "Title")
    val title: String,
    @ColumnInfo(name = "UploadDate")
    val date: String,
    @ColumnInfo(name = "NoticeType")
    val type: String
) : Parcelable