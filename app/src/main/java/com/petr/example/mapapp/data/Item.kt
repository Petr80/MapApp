package com.petr.example.mapapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var itemId: Long,
    @ColumnInfo(name = "et_1") var edit1: String,
    @ColumnInfo(name = "et_2") var edit2: String,
    @ColumnInfo(name = "et_3") var edit3: String,
    @ColumnInfo(name = "roll_1") var roll1: String = "",
    @ColumnInfo(name = "roll_2") var roll2: String = "",
    @ColumnInfo(name = "image_url_1") var imageUrl1: String,
)
