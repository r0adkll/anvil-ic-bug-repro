package com.r0adkll.anvil_ic

import android.app.Application
import com.r0adkll.anvil_ic.di.DaggerAppComponent
import com.r0adkll.anvil_ic.di.UserComponent
import com.r0adkll.common.ComponentHolder

class SampleApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    // Initialize DI
    ComponentHolder.components += DaggerAppComponent.factory().create(this)
    ComponentHolder.components += ComponentHolder.component<UserComponent.Parent>()
      .userComponentFactory
      .create()
  }
}