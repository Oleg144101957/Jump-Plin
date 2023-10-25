package com.snapd.di

import com.snapd.data.providers.AdbProviderImpl
import com.snapd.data.providers.GaidProviderImpl
import com.snapd.data.providers.RefProviderImpl
import com.snapd.domain.providers.AdbProvider
import com.snapd.domain.providers.GaidProvider
import com.snapd.domain.providers.RefProvider
import org.koin.dsl.module

val dataModule = module {

    single<AdbProvider> {
        AdbProviderImpl(context = get())
    }

    single<GaidProvider>{
        GaidProviderImpl(context = get())
    }

    single<RefProvider>{
        RefProviderImpl(context = get())
    }

}