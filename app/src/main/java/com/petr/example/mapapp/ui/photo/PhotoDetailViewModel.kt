package com.petr.example.mapapp.ui.photo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petr.example.mapapp.data.PhotoRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class PhotoDetailViewModel @AssistedInject constructor(private val photoRepository: PhotoRepository, @Assisted private val photoId: Long,
) : ViewModel() {

    val photo = photoRepository.getPhoto(photoId)

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(photoId: Long): PhotoDetailViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedFactory, photoId: Long):
                ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T: ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(photoId) as T
            }
        }
    }

}