package com.snapd.data.providers

import android.content.Context
import android.provider.Settings
import com.snapd.domain.providers.AdbProvider

class AdbProviderImpl(private val context: Context) : AdbProvider {
    override fun provideAdb(): String {
        return Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED)
    }
}