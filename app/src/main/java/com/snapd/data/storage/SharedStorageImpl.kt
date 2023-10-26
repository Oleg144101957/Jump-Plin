package com.snapd.data.storage

import android.content.Context
import com.snapd.domain.storage.AppStorage

const val STORAGE_NAME = "STORAGE_NAME"
const val DESTINATION_KEY = "DESTINATION_KEY"
const val EMPTY_DESTINATION = "EMPTY_DESTINATION"
const val HARM_DESTINATION = "HARM_DESTINATION"

class SharedStorageImpl(context: Context) : AppStorage{

    private val sharedPref = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)

    override fun saveDestination(url: String) {
        val currentDestination = sharedPref.getString(DESTINATION_KEY, EMPTY_DESTINATION)

        if (currentDestination != HARM_DESTINATION){
            sharedPref.edit().putString(DESTINATION_KEY, url).apply()
        }
    }

    override fun getDestination(): String {
        return sharedPref.getString(DESTINATION_KEY, EMPTY_DESTINATION) ?: EMPTY_DESTINATION
    }
}