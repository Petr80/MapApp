package com.petr.example.mapapp.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(private val photoDao: PhotoDao) {

    fun getAllPhotos() = photoDao.getAllPhotos()
    fun getPhoto(photoId: Long) = photoDao.getPhoto(photoId)
    suspend fun insertPhoto(photo: Photo) = photoDao.insertPhoto(photo)
    // delete Image
}