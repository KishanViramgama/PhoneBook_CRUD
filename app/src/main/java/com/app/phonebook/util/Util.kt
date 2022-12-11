package com.app.phonebook.util

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.phonebook.database.DatabaseClient
import com.app.phonebook.ui.home.item.PhoneBook
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Util {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabaseClient(context: Context): DatabaseClient {
        return DatabaseClient(context)
    }

    @Provides
    @Singleton
    fun provideMethod(): Method {
        return Method()
    }

    @Provides
    @Singleton
    fun provideMutableLiveData(): MutableLiveData<PhoneBook> = MutableLiveData()

    @Singleton
    fun provideLiveData(mutableData: MutableLiveData<PhoneBook>): LiveData<PhoneBook> = mutableData

}