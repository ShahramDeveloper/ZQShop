package com.shahram.zqshop.di

import android.content.Context
import com.shahram.zqshop.utils.USER_DATA
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val ZQShopModules = module {
    single { androidContext().getSharedPreferences(USER_DATA, Context.MODE_PRIVATE) }
}