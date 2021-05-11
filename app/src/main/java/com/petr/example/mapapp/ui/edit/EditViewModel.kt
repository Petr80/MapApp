package com.petr.example.mapapp.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petr.example.mapapp.data.Item
import com.petr.example.mapapp.data.ItemRepository
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch

class EditViewModel @AssistedInject constructor(
        private val itemRepository: ItemRepository,
        @Assisted private val itemId: Long
) : ViewModel() {

    val item = itemRepository.getItem(itemId)

    fun getItem(itemId: Long) = itemRepository.getItem(itemId)

    fun saveItem(itemId1: Long, lat: Double, lng: Double, edit_1: String, edit_2: String, edit_3: String, roll_1: String, roll_2: String, imageUrl1: String) {

        val itemToSave = Item(itemId1, lat, lng, edit_1, edit_2, edit_3, roll_1, roll_2, imageUrl1)

        viewModelScope.launch {
            var actualId = itemId1

            if (itemId1 > 0) {
                itemRepository.updateItem(itemToSave)
            } else {
                actualId = insertItem(itemToSave)
            }
        }
    }

    private suspend fun insertItem(itemToSave: Item): Long {
        return itemRepository.insertItem(itemToSave)
    }


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