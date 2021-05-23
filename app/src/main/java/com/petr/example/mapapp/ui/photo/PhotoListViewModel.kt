package com.petr.example.mapapp.ui.photo

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.petr.example.mapapp.data.Photo
import com.petr.example.mapapp.data.PhotoRepository
import kotlinx.coroutines.launch

class PhotoListViewModel @ViewModelInject internal constructor(private val photoRepository: PhotoRepository, @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val photos: LiveData<List<Photo>> = photoRepository.getAllPhotos()

    fun savePhoto(path: String) {
        val photoToInsert = Photo(photoId = 0, photoUrl = path)
        viewModelScope.launch {
            insertPhoto(photoToInsert)
        }
    }

    private suspend fun insertPhoto(photoToInsert: Photo): Long {
        return photoRepository.insertPhoto(photoToInsert)
    }
}
