package com.app.phonebook.util

data class LiveDataType<out T>(val type: Type, val position: Int, val data: T?) {

    companion object {

        fun <T> callObserver(data: T?, position: Int, type: Type): LiveDataType<T> {
            return LiveDataType(type, position, data)
        }

    }
}