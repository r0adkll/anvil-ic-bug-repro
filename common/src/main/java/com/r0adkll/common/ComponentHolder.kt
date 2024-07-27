package com.r0adkll.common

object ComponentHolder {
  val components = mutableSetOf<Any>()

  inline fun <reified T> component(): T {
    return components
      .filterIsInstance<T>()
      .singleOrNull() ?: error("Unable to find a component of type ${T::class.java.name}")
  }
}

class ScopedComponentHolder(val component: Any) {

  inline fun <reified T> component(): T = component as? T
    ?: error("Component(${component::class.java.name}) is not an instance of (${T::class.java.name})")

  @Suppress("UNCHECKED_CAST")
  fun <T> component(clazz: Class<T>): T = component as? T
    ?: error("Unable to find a component of type ${clazz.name}")
}
