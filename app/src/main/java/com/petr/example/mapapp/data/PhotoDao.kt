package com.petr.example.mapapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photos")
    fun getAllPhotos(): LiveData<List<Photo>>

    @Query("SELECT * FROM photos WHERE id = :imageId")
    fun getPhoto(imageId: Long): LiveData<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(image: Photo): Long

    // Delete a image by id. @return the number of pet deleted. This should always be 1.
    @Query("DELETE FROM photos WHERE id = :photoId")
    suspend fun deletePhotoById(photoId: Long): Int
}