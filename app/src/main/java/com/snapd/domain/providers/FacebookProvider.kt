package com.snapd.domain.providers

interface FacebookProvider {

    suspend fun provideFacebook() : String

}