package com.petr.example.mapapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var itemId: Long,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "birth_date") var birthDate: String,
    @ColumnInfo(name = "weight") var weight: String,
    @ColumnInfo(name = "colour") var colour: String,
    @ColumnInfo(name = "dist_marks") var distMarks: String = "",
    @ColumnInfo(name = "street") var street: String = "",
    @ColumnInfo(name = "street_nbr") var streetNbr: String = "",
    @ColumnInfo(name = "town") var town: String = "",
    @ColumnInfo(name = "state") var state: String = "",
)
