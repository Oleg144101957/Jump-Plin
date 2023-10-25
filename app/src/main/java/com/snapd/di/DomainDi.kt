package com.snapd.di

import com.snapd.domain.features.CollectTrackDataUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        CollectTrackDataUseCase()
    }

}