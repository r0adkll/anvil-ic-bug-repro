package com.r0adkll.anvil_ic.di.module

import com.r0adkll.common.coroutines.DispatcherProvider
import com.r0adkll.common.scopes.AppScope
import com.r0adkll.common.scopes.SingleIn
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(AppScope::class)
object CoroutinesModule {

  @Provides
  @SingleIn(AppScope::class)
  fun provideDispatcherProvider(): DispatcherProvider = DispatcherProvider.default()
}