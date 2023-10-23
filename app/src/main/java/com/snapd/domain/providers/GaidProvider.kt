package com.snapd.domain.providers

interface GaidProvider {

    suspend fun provideGaid() : String

}