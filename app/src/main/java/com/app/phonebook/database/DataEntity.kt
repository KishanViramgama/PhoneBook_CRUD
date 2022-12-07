package com.app.phonebook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.phonebook.ui.createcontact.item.PhoneBook

@Dao
interface DataEntity {

    @Insert
    fun insert(vararg phoneBook: PhoneBook)

    @Query("SELECT * FROM PhoneBook")
    fun getData(): MutableList<PhoneBook>

}