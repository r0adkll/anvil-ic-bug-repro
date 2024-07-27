package com.r0adkll.common

import com.r0adkll.common.scopes.SingleIn
import com.r0adkll.common.scopes.UserScope
import com.squareup.anvil.annotations.ContributesTo
import dagger.Lazy
import javax.inject.Inject

interface FeatureInjector<T, S> {
  fun inject(target: T, factory: () -> S): ScopedComponentHolder
}

@ContributesTo(UserScope::class)
interface FeatureInjectorComponent {
  val featureInjectorProvider: FeatureInjectorProvider
}


@SingleIn(UserScope::class)
class FeatureInjectorProvider @Inject constructor(
  private val featureInjectors: Lazy<Map<Class<*>, FeatureInjector<*, *>>>,
) {

  fun get(clazz: Class<*>): FeatureInjector<*, *>? {
    return featureInjectors.get()[clazz]
  }
}

inline fun <reified T> T.injectFeature() = this.injectFeature<T, Unit>() { }

@Suppress("UNCHECKED_CAST")
inline fun <reified T, reified S> T.injectFeature(
  noinline factory: () -> S,
): ScopedComponentHolder {
  val featureInjectorComponent = ComponentHolder.component<FeatureInjectorComponent>()
  val featureInjector = featureInjectorComponent
    .featureInjectorProvider
    .get(T::class.java) as? FeatureInjector<T, S>

  return featureInjector?.inject(
    target = this,
    factory = factory,
  ) ?: error(
    """
    Unable to find any FeatureInjector for target class ${T::class.java.simpleName} with a
    dependency factory of type ${S::class.java.simpleName}.
    """.trimIndent(),
  )
}
