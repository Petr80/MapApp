package com.petr.example.mapapp.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petr.example.mapapp.data.ItemRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class EditViewModel @AssistedInject constructor(
        private val itemRepository: ItemRepository,
        @Assisted private val itemId: Long
) : ViewModel() {

    val item = itemRepository.getItem(itemId)

    fun getPet(petId: Long) = itemRepository.getItem(itemId)

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(itemId: Long): EditViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedFactory, itemId: Long):
                ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(itemId) as T
            }
        }
    }

}