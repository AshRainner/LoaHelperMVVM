package com.lostark.loahelper.database.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="Key")
data class Key(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Key")
    val key: String
)
