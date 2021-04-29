package com.petr.example.mapapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ItemDao {
    @Query("SELECT * FROM items ORDER BY et_1")
    fun getItems(): LiveData<List<Item>>

    @Query("SELECT * FROM items WHERE id = :itemId")
    fun getItem(itemId: Long): LiveData<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(pet: Item): Long

    // Update a pet. @param pet - pet to be updated  @return the number of tasks updated. This should always be 1.
    @Update
    suspend fun updateItem(pet: Item): Int

    // Delete a task by id. @return the number of pet deleted. This should always be 1.
    @Query("DELETE FROM items WHERE id = :itemId")
    suspend fun deleteItemById(itemId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Item>)

    @Query("DELETE FROM items")
    suspend fun deleteAllItems()
}
