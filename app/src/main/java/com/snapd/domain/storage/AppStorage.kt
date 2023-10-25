package com.snapd.domain.storage

interface AppStorage  {
    fun saveDestination(url: String)
    fun getDestination() : String

}