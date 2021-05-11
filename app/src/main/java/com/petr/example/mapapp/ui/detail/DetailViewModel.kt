package com.petr.example.mapapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.petr.example.mapapp.data.ItemRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

class DetailViewModel @AssistedInject constructor(
        itemRepository: ItemRepository,
        @Assisted private val itemId: Long
) : ViewModel() {

    val item = itemRepository.getItem(itemId)

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(itemId: Long): DetailViewModel
    }

    companion object {
        fun provideFactory(assistedFactory: AssistedFactory, itemId: Long):
                ViewModelProvider.Factory = object: ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T: ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(itemId) as T
            }
        }
    }
}