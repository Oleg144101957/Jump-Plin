package com.snapd.di

import com.snapd.domain.features.CollectTrackDataUseCase
import com.snapd.presantation.viewModels.JumpGameViewModel
import org.koin.dsl.module


val presentationModule = module {

    factory {
        JumpGameViewModel()
    }
}