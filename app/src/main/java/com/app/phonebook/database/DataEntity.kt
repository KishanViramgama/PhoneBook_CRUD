package com.app.phonebook.database

import androidx.room.*
import com.app.phonebook.ui.home.item.PhoneBook
import kotlinx.coroutines.flow.Flow

@Dao
interface DataEntity {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(phoneBook: PhoneBook): Long

    @Query("SELECT * FROM PhoneBook")
    fun getAllContact(): MutableList<PhoneBook>

    // get single transaction by id
    @Query("SELECT * FROM PhoneBook WHERE id = :id")
    fun getSingleContact(id: String): PhoneBook

    // delete transaction by id
    @Query("DELETE FROM PhoneBook WHERE id = :id")
    suspend fun deleteContactById(id: Int)

}