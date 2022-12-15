package com.app.phonebook.database

import androidx.room.*
import com.app.phonebook.ui.home.item.PhoneBook
import kotlinx.coroutines.flow.Flow

@Dao
interface DataEntity {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(phoneBook: PhoneBook): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserContact(phoneBook: PhoneBook): Int

    @Query("SELECT * FROM PhoneBook")
    fun getAllContact(): MutableList<PhoneBook>

    @Query("SELECT * FROM PhoneBook WHERE id = :id")
    fun getSingleContact(id: String): PhoneBook

    @Query("DELETE FROM PhoneBook WHERE id = :id")
    suspend fun deleteContactById(id: Int)

}