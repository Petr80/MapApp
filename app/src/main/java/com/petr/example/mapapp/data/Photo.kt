package com.petr.example.mapapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var photoId: Long = 0,
    @ColumnInfo(name = "photo_url") var photoUrl: String
)