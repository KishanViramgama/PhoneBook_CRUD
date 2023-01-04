package com.app.phonebook.database

import androidx.room.*
import com.app.phonebook.ui.home.item.PhoneBook
import kotlinx.coroutines.flow.Flow

@Dao
interface DataEntity {

    //Insert contact
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(phoneBook: PhoneBook): Long

    //Update contact
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserContact(phoneBook: PhoneBook): Int

    //Get all contact
    @Query("SELECT * FROM PhoneBook")
    fun getAllContact(): MutableList<PhoneBook>

    //Get single contact
    @Query("SELECT * FROM PhoneBook WHERE id = :id")
    fun getSingleContact(id: String): PhoneBook

    //Delete single contact
    @Query("DELETE FROM PhoneBook WHERE id = :id")
    suspend fun deleteContactById(id: String) :Int

}