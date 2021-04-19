package com.petr.example.mapapp.ui.items

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.petr.example.mapapp.data.Item
import com.petr.example.mapapp.data.ItemRepository

class ItemsListViewModel @ViewModelInject internal constructor(itemRepository: ItemRepository, @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val items: LiveData<List<Item>> = itemRepository.getItems()
}