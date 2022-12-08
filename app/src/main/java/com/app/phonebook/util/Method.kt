package com.app.phonebook.util

import android.util.Patterns

class Method {

    fun isValidMail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}