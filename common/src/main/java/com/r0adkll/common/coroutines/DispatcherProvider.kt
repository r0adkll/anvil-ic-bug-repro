package com.r0adkll.common.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DispatcherProvider(
  val main: CoroutineDispatcher,
  val io: CoroutineDispatcher,
) {
  companion object {

    fun default(): DispatcherProvider = DispatcherProvider(
      main = Dispatchers.Main,
      io = Dispatchers.IO,
    )
  }
}