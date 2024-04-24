package com.r0adkll.anvil_ic.di.module;

import com.r0adkll.common.coroutines.DispatcherProvider;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("com.r0adkll.common.scopes.SingleIn")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class CoroutinesModule_ProvideDispatcherProviderFactory implements Factory<DispatcherProvider> {
  @Override
  public DispatcherProvider get() {
    return provideDispatcherProvider();
  }

  public static CoroutinesModule_ProvideDispatcherProviderFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DispatcherProvider provideDispatcherProvider() {
    return Preconditions.checkNotNullFromProvides(CoroutinesModule.INSTANCE.provideDispatcherProvider());
  }

  private static final class InstanceHolder {
    private static final CoroutinesModule_ProvideDispatcherProviderFactory INSTANCE = new CoroutinesModule_ProvideDispatcherProviderFactory();
  }
}
