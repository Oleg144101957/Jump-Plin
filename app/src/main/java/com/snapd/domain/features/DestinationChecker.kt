package com.snapd.domain.features

import com.snapd.data.storage.HARM_DESTINATION
import com.snapd.domain.storage.AppStorage


const val DELI = "DELI"
class DestinationChecker(private val destination: String, private val storage: AppStorage) {

    private var listOfDeli = listOf("ps://ju", "mppl", "in", ".site/")

    fun checkDestination(): String {

        var result = ""

        val sb = StringBuilder("htt")

        listOfDeli.forEach {
            sb.append(it)
        }

        if (destination.contains(sb.toString())) {
            result = HARM_DESTINATION
        } else {
            //save link
            saveDestination(destination)
        }

        listOfDeli = listOf("key", "Jump Lin")

        return result
    }


    private fun saveDestination(destination: String){

        var listOfBase = listOf("//ju", "mppl", "in", ".site/")

        val currentDestination = storage.getDestination()

        val base = StringBuilder("https")

        listOfBase.forEach {
            base.append(it)
        }

        listOfBase = listOf("sdf", "ds", "dsa")


        if (!destination.contains(base) && !currentDestination.startsWith("http")){
            storage.saveDestination(destination)
        }

        listOfBase.forEach {
            base.append(it)
        }
    }
}