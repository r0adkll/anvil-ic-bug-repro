package com.r0adkll.anvil_ic.di

import android.app.Application
import com.r0adkll.common.scopes.AppScope
import com.r0adkll.common.scopes.SingleIn
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component

@SingleIn(AppScope::class)
@MergeComponent(AppScope::class)
interface AppComponent {

  @MergeComponent.Factory
  interface Factory {
    fun create(
      @BindsInstance application: Application,
    ): AppComponent
  }
}