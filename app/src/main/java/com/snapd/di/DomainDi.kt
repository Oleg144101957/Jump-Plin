package com.snapd.di

import com.snapd.data.providers.AdbProviderImpl
import com.snapd.domain.features.CollectTrackDataUseCase
import com.snapd.domain.providers.AdbProvider
import org.koin.dsl.module

val domainModule = module {

    factory {
        CollectTrackDataUseCase(adbProvider = get())
    }

}