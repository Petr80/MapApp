package com.petr.example.mapapp.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(private val itemDao: ItemDao){

    // Pet List and Detail Fragment
    fun getItems() = itemDao.getItems()
    fun getItem(itemId: Long) = itemDao.getItem(itemId)
    suspend fun insertItem(item: Item) = itemDao.insertItem(item)
    suspend fun updateItem(item: Item) = itemDao.updateItem(item)
    // delete Pet

}