package com.snapd.di

import com.snapd.data.providers.AdbProviderImpl
import com.snapd.domain.providers.AdbProvider
import org.koin.dsl.module

val dataModule = module {

    single<AdbProvider> {
        AdbProviderImpl(context = get())
    }

}