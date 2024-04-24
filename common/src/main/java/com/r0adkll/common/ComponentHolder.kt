package com.r0adkll.common

object ComponentHolder {
  val components = mutableSetOf<Any>()

  inline fun <reified T> component(): T {
    return components
      .filterIsInstance<T>()
      .singleOrNull() ?: error("Unable to find a component of type ${T::class.java.name}")
  }
}