package com.petr.example.mapapp.ui.map

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.petr.example.mapapp.data.Item
import com.petr.example.mapapp.data.ItemRepository

class MapViewModel
@ViewModelInject
constructor(
    itemRepository: ItemRepository
) : ViewModel() {

    val items: LiveData<List<Item>> = itemRepository.getItems()
}