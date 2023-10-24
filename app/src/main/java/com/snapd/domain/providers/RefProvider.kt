package com.snapd.domain.providers

interface RefProvider {
    suspend fun provideRef() : String

}